package com.messenger.friendsapp.domain.repository;

import com.messenger.friendsapp.domain.entity.Blacklist;
import com.messenger.friendsapp.domain.valueobject.FullName;

import java.util.UUID;

public interface BlacklistRepository {
    boolean isRequesterBlocked(UUID requesterId, UUID addresseeId);
    void save(Blacklist blacklist);
    void delete(UUID requesterId, UUID addresseeId);
    void updateAllAddresseeIdFullName(UUID addresseeId, FullName fullName);
}
