package com.messenger.friendsapp.application.query.handler;

import com.messenger.friendsapp.application.query.IsUserInBlacklistQuery;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class IsUserInBlacklistQueryHandler {
    private final BlacklistRepository blacklistRepository;

    public IsUserInBlacklistQueryHandler(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    /**
     * Checks if user is in other's blacklist
     * @param query CQS command for appropriate handler
     * @return {@code true} if user is in other's blacklist and {@code false} otherwise
     */
    public boolean handle(@NonNull IsUserInBlacklistQuery query) {
        return blacklistRepository.isRequesterBlocked(query.getRequesterId(), query.getAddresseeId());
    }
}
