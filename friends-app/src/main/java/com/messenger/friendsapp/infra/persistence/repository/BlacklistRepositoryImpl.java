package com.messenger.friendsapp.infra.persistence.repository;

import com.messenger.friendsapp.domain.entity.Blacklist;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.infra.persistence.mapper.BlacklistEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BlacklistRepositoryImpl implements BlacklistRepository {
    private final BlacklistRepositoryJpa blacklistRepositoryJpa;

    public BlacklistRepositoryImpl(BlacklistRepositoryJpa blacklistRepositoryJpa) {
        this.blacklistRepositoryJpa = blacklistRepositoryJpa;
    }

    @Override
    public Optional<Blacklist> getByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId) {
        return blacklistRepositoryJpa
                .findByRequesterIdAndAddresseeId(requesterId, addresseeId)
                .map(BlacklistEntityMapper::mapToDomainModel);
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
    public void delete(Blacklist blacklist) {
        var bl = BlacklistEntityMapper.mapToEntity(blacklist);
        blacklistRepositoryJpa.delete(bl);
    }

    @Override
    public void updateFullNameById(UUID userId, FullName fullName) {
        var optionalFriendship = blacklistRepositoryJpa.findById(userId);

        if (optionalFriendship.isEmpty()) {
            return;
        }

        var friendship = optionalFriendship.get();

        friendship.setAddresseeFirstName(fullName.getFirstName());
        friendship.setAddresseeMiddleName(fullName.getMiddleName());
        friendship.setAddresseeLastName(fullName.getLastName());

        blacklistRepositoryJpa.save(friendship);
    }
}
