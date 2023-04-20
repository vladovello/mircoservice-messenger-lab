package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.friendsapp.infra.persistence.entity.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepositoryJpa extends JpaRepository<FriendshipEntity, UUID>,
        JpaSpecificationExecutor<FriendshipEntity>,
        PagingAndSortingRepository<FriendshipEntity, UUID> {
    boolean existsByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);

    List<FriendshipEntity> findAllByRequesterIdAndFriendshipStatus(UUID requesterId, FriendshipStatus friendshipStatus);

    List<FriendshipEntity> findAllByRequesterId(UUID requesterId);
    Optional<FriendshipEntity> findByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
}
