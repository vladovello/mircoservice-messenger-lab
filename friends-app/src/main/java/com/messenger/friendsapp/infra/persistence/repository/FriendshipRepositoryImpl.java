package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.domain.entity.Friendship;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.infra.persistence.entity.metadata.FriendshipEntityFields;
import com.messenger.friendsapp.infra.persistence.mapper.FriendshipEntityMapper;
import com.messenger.friendsapp.infra.persistence.spec.FriendshipSpecs;
import com.messenger.sharedlib.parameter.order.SortingOrder;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.messenger.sharedlib.parameter.order.SortingOrder.ASC;

@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {
    private final FriendshipRepositoryJpa friendshipRepositoryJpa;
    private final BlacklistRepository blacklistRepository;

    public FriendshipRepositoryImpl(
            FriendshipRepositoryJpa friendshipRepositoryJpa,
            BlacklistRepository blacklistRepository
    ) {
        this.friendshipRepositoryJpa = friendshipRepositoryJpa;
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public boolean isAddedToFriendList(UUID requesterId, UUID addresseeId) {
        return friendshipRepositoryJpa.existsByRequesterIdAndAddresseeId(requesterId, addresseeId);
    }

    @Override
    public void updateFullNameById(UUID userId, FullName fullName) {
        var optionalFriendship = friendshipRepositoryJpa.findById(userId);

        if (optionalFriendship.isEmpty()) {
            return;
        }

        var friendship = optionalFriendship.get();

        friendship.setAddresseeFirstName(fullName.getFirstName());
        friendship.setAddresseeMiddleName(fullName.getMiddleName());
        friendship.setAddresseeLastName(fullName.getLastName());

        friendshipRepositoryJpa.save(friendship);
    }

    @Override
    public List<Friendship> findAllFriendsPaginatedByFullName(
            int pageNumber,
            int pageSize,
            UUID userId,
            String fullName
    ) {
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC);

        var spec = FriendshipSpecs.filterFriendships(userId, fullName, null, null);
        var page = friendshipRepositoryJpa.findAll(spec, pageRequest);

        var friendships = FriendshipEntityMapper.mapToDomainModelList(page.getContent());
        removeBlockedAndDeletedUsers(friendships);
        return friendships;
    }

    @Override
    public List<Friendship> findAllFriendsPaginatedByParams(
            int pageNumber,
            int pageSize,
            UUID userId,
            @NonNull DiscreteParam<String> fullName,
            @NonNull IntervalParam<LocalDate> additionDate,
            @NonNull DiscreteParam<UUID> friendId
    ) {
        var pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                getFriendshipFilterParamsSorting(fullName, additionDate, friendId)
        );

        var page = friendshipRepositoryJpa.findAll(FriendshipSpecs.filterFriendships(
                userId,
                fullName.getValue(),
                additionDate.getInterval(),
                friendId.getValue()
        ), pageRequest);

        var friendships = FriendshipEntityMapper.mapToDomainModelList(page.getContent());
        removeBlockedAndDeletedUsers(friendships);
        return friendships;
    }

    @Override
    public Optional<Friendship> findFriend(UUID requesterId, UUID addresseeId) {
        if (blacklistRepository.isRequesterBlocked(requesterId, addresseeId)) {
            return Optional.empty();
        }

        var optionalFriend = friendshipRepositoryJpa.findByRequesterIdAndAddresseeId(requesterId, addresseeId);

        if (optionalFriend.isEmpty()) {
            return Optional.empty();
        }

        if (optionalFriend.get().getDeletionDate() != null) {
            return Optional.empty();
        }

        return optionalFriend.map(FriendshipEntityMapper::mapToDomainModel);
    }

    @Override
    public void save(Friendship friendship) {
        var fe = FriendshipEntityMapper.mapToEntity(friendship);
        fe.setDeletionDate(null);
        friendshipRepositoryJpa.save(fe);
    }

    @Override
    public void delete(Friendship friendship) {
        var fe = FriendshipEntityMapper.mapToEntity(friendship);
        fe.setDeletionDate(LocalDate.now());
        friendshipRepositoryJpa.save(fe);
    }

    // Кал, говно, говнище.
    private void removeBlockedAndDeletedUsers(@NonNull List<Friendship> friendshipList) {
        friendshipList.removeIf(friendship -> {
            var isAddresseeBlocked = blacklistRepository.isRequesterBlocked(
                    friendship.getRequesterId(),
                    friendship.getAddressee().getId()
            );

            var isRequesterBlocked = blacklistRepository.isRequesterBlocked(
                    friendship.getAddressee().getId(),
                    friendship.getRequesterId()
            );

            var fe = friendshipRepositoryJpa.findByRequesterIdAndAddresseeId(
                    friendship.getRequesterId(),
                    friendship.getRequesterId()
            );

            boolean isDeleted = fe.isPresent() && fe.get().getDeletionDate() != null;

            return isRequesterBlocked || isAddresseeBlocked || isDeleted;
        });
    }

    // TODO: выраженные аргументы. Челы изменяются - это не очевидно, т.к. нет возвращаемого значения
    private @NonNull Sort getFriendshipFilterParamsSorting(
            DiscreteParam<String> fullName,
            IntervalParam<LocalDate> additionDate,
            DiscreteParam<UUID> friendId
    ) {
        if (fullName == null) {
            fullName = DiscreteParam.defaultNullValue();
        }
        if (additionDate == null) {
            additionDate = IntervalParam.defaultNullValue();
        }
        if (friendId == null) {
            friendId = DiscreteParam.defaultNullValue();
        }

        return getSortParam(FriendshipEntityFields.FULL_NAME_NAME, fullName.getSortingOrder())
                .and(getSortParam(FriendshipEntityFields.ADDITION_DATE_NAME, additionDate.getSortingOrder()))
                .and(getSortParam(FriendshipEntityFields.ADDRESSEE_ID, friendId.getSortingOrder()));
    }

    private @NonNull Sort getSortParam(String propertyName, @NonNull SortingOrder sortingOrder) {
        if (sortingOrder == ASC) return Sort.by(Sort.Direction.ASC, propertyName);
        return Sort.by(Sort.Direction.DESC, propertyName);
    }
}
