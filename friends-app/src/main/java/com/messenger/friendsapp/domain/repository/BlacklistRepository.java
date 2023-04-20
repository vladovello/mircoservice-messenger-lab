package com.messenger.friendsapp.domain.repository;

import com.messenger.friendsapp.domain.entity.Blacklist;

import java.util.UUID;

public interface BlacklistRepository {
    boolean isRequesterBlocked(UUID requesterId, UUID addresseeId);
    void save(Blacklist blacklist);
    void delete(UUID requesterId, UUID addresseeId);
}
