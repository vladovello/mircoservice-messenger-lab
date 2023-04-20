package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.infra.persistence.entity.BlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlacklistRepositoryJpa extends JpaRepository<BlacklistEntity, UUID> {
    boolean existsByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
    void deleteByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
}
