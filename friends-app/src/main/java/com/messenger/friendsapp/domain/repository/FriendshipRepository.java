package com.messenger.friendsapp.domain.repository;

import com.messenger.friendsapp.domain.entity.Friendship;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Запрос на получение друзей для userId происходит следующим образом:
 *  1. Сначала ищутся все не BLOCKED пользователи в списке Friendship, где requesterId = userId
 *  2. Потом ищутся все пользователи, у которых не стоит BLOCKED и где addresseeId = userId
 */
public interface FriendshipRepository {
    boolean isAddedToFriendList(UUID requesterId, UUID addresseeId);

    List<Friendship> findAllByRequesterIdAndFriendshipStatus(UUID requesterId, FriendshipStatus friendshipStatus);

    List<Friendship> findAllByRequesterId(UUID requesterId);

    List<Friendship> findAllFriendsPaginatedByFullName(int pageNumber, int pageSize, UUID userId, String fullName);

    List<Friendship> findAllFriendsPaginatedByParams(
            int pageNumber,
            int pageSize,
            UUID userId,
            DiscreteParam<String> fullName,
            IntervalParam<LocalDate> additionDate,
            DiscreteParam<UUID> friendId
    );

    Optional<Friendship> findFriend(UUID requesterId, UUID addresseeId);

    // Renew additionDate and make deletionDate null
    void save(Friendship friendship);

    // Renew deletionDate
    void delete(Friendship friendship);
}
