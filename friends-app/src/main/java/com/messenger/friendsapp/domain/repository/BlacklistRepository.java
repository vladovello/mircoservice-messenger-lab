package com.messenger.friendsapp.domain.repository;

import com.messenger.friendsapp.domain.entity.Blacklist;
import com.messenger.friendsapp.domain.valueobject.FullName;

import java.util.Optional;
import java.util.UUID;

public interface BlacklistRepository {
    Optional<Blacklist> getByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);
    boolean isRequesterBlocked(UUID requesterId, UUID addresseeId);
    void save(Blacklist blacklist);
    void delete(Blacklist blacklist);
    void updateFullNameById(UUID userId, FullName fullName);
}
