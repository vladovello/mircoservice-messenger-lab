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

    public boolean handle(@NonNull IsUserInBlacklistQuery query) {
        return blacklistRepository.isRequesterBlocked(query.getRequesterId(), query.getAddresseeId());
    }
}
