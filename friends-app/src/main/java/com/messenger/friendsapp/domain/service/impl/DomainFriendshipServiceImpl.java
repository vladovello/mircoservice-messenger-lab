package com.messenger.friendsapp.domain.service.impl;

import com.messenger.friendsapp.domain.aggregate.Friendship;
import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.service.DomainFriendshipService;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.friendsapp.shared.Unit;
import io.vavr.control.Either;
import lombok.NonNull;

import javax.transaction.Transactional;
import java.util.UUID;

public class DomainFriendshipServiceImpl implements DomainFriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final BlacklistRepository blacklistRepository;

    public DomainFriendshipServiceImpl(
            FriendshipRepository friendshipRepository,
            BlacklistRepository blacklistRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.blacklistRepository = blacklistRepository;
    }

    /*
     * 1. Проверить, не добавлен ли пользователь уже в друзья. Если добавлен, просто выходим из метода
     * 2. Проверить, не заблокировали ли мы пользователя. Если да, то нужно уведомить о том, что пользователь заблокирован
     * 3. Проверить, добавлял ли нас пользователь в друзья. Если да, то нужно поставить статус MUTUAL у обоих.
     */
    @Override
    @Transactional
    public Either<UserIsBlockedException, Unit> addToFriendshipList(UUID requesterId, @NonNull Addressee addressee) {
        var isAdded = friendshipRepository.isAddedToFriendList(requesterId, addressee.getId());

        if (isAdded) {
            return Either.right(Unit.INSTANCE);
        }

        if (blacklistRepository.isUserBlocked(requesterId, addressee.getId())) {
            return Either.left(new UserIsBlockedException());
        }

        var optionalAddresseeFriendship = friendshipRepository.findFriend(addressee.getId(), requesterId);
        Friendship requesterFriendship;

        if (optionalAddresseeFriendship.isPresent()) {
            var addresseeFriendship = optionalAddresseeFriendship.get();
            addresseeFriendship.setMutualStatus();

            requesterFriendship = Friendship.createNewFriendship(requesterId, addressee, FriendshipStatus.MUTUAL);

            friendshipRepository.save(addresseeFriendship);
        } else {
            requesterFriendship = Friendship.createNewFriendship(requesterId, addressee);
        }

        friendshipRepository.save(requesterFriendship);

        return Either.right(Unit.INSTANCE);
    }

    /*
     * 1. Если дружба была невзаимной, просто удаляем из друзей
     * 2. Если дружба была взаимной, меняем состояние на PENDING (у того, кого удалили)
     */
    @Override
    @Transactional
    public void removeFromFriendshipList(UUID requesterId, UUID addresseeId) {
        var optionalFriendship = friendshipRepository.findFriend(requesterId, addresseeId);

        if (optionalFriendship.isEmpty()) {
            return;
        }

        var friendship = optionalFriendship.get();

        var optionalAddresseeFriendship = friendshipRepository.findFriend(addresseeId, requesterId);

        if (optionalAddresseeFriendship.isPresent()) {
            var addresseeFriendship = optionalAddresseeFriendship.get();
            addresseeFriendship.setPendingStatus();

            friendshipRepository.save(addresseeFriendship);
        }

        friendshipRepository.delete(friendship.getId());
    }
}
