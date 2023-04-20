package com.messenger.friendsapp.domain.service.impl;

import com.messenger.friendsapp.domain.aggregate.Blacklist;
import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.service.DomainBlacklistService;
import lombok.NonNull;

import java.util.UUID;

public class DomainBlacklistServiceImpl implements DomainBlacklistService {
    private final BlacklistRepository blacklistRepository;

    public DomainBlacklistServiceImpl(
            BlacklistRepository blacklistRepository
    ) {
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public void addToBlacklist(UUID requesterId, @NonNull Addressee addressee) {
        if (blacklistRepository.isUserBlocked(requesterId, addressee.getId())) {
            return;
        }

        blacklistRepository.save(Blacklist.createNewBlacklist(requesterId, addressee));
    }

    @Override
    public void removeFromBlacklist(UUID requesterId, UUID addresseeId) {
        if (!blacklistRepository.isUserBlocked(requesterId, addresseeId)) {
            return;
        }

        blacklistRepository.delete(requesterId, addresseeId);
    }
}
