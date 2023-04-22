package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.domain.entity.Blacklist;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.infra.persistence.mapper.BlacklistEntityMapper;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class BlacklistRepositoryImpl implements BlacklistRepository {
    private final BlacklistRepositoryJpa blacklistRepositoryJpa;

    public BlacklistRepositoryImpl(BlacklistRepositoryJpa blacklistRepositoryJpa) {
        this.blacklistRepositoryJpa = blacklistRepositoryJpa;
    }

    @Override
    public boolean isRequesterBlocked(UUID requesterId, UUID addresseeId) {
        return blacklistRepositoryJpa.existsByRequesterIdAndAddresseeId(requesterId, addresseeId);
    }

    @Override
    public void save(Blacklist blacklist) {
        var bl = BlacklistEntityMapper.mapToEntity(blacklist);
        blacklistRepositoryJpa.save(bl);
    }

    @Override
    public void delete(UUID requesterId, UUID addresseeId) {
        blacklistRepositoryJpa.deleteByRequesterIdAndAddresseeId(requesterId, addresseeId);
    }

    @Override
    public void updateAllAddresseeIdFullName(UUID addresseeId, @NonNull FullName fullName) {
        var friendships = blacklistRepositoryJpa.findAllByAddresseeId(addresseeId);
        friendships.forEach(blacklistEntity -> {
            blacklistEntity.setAddresseeFirstName(fullName.getFirstName());
            blacklistEntity.setAddresseeMiddleName(fullName.getMiddleName());
            blacklistEntity.setAddresseeLastName(fullName.getLastName());
        });
    }
}
