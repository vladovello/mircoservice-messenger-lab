package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.infra.persistence.entity.BlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlacklistRepositoryJpa extends JpaRepository<BlacklistEntity, UUID> {
    Optional<BlacklistEntity> findByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);

    boolean existsByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
}
