package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.infra.persistence.entity.BlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlacklistRepositoryJpa extends JpaRepository<BlacklistEntity, UUID> {
    boolean existsByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
    void deleteByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
    List<BlacklistEntity> findAllByAddresseeId(UUID addresseeId);
}
