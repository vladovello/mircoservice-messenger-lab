package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.user.repository.BlacklistRepository;

import java.util.UUID;

public class BlacklistRepositoryImpl implements BlacklistRepository {
    // TODO: 16.05.2023 implement
    @Override
    public boolean isUserIsBlacklistedByOther(UUID userId, UUID otherUserId) {
        return false;
    }
}
