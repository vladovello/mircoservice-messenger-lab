package com.messenger.chat.domain.user.repository;

import java.util.UUID;

public interface BlacklistRepository {
    boolean isUserIsBlacklistedByOther(UUID userId, UUID otherUserId);
}
