package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Addressee;

import java.util.UUID;

public interface DomainBlacklistService {
    void addToBlacklist(UUID requesterId, Addressee addressee);

    void removeFromBlacklist(UUID requesterId, UUID addresseeId);
}
