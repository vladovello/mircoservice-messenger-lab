package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.RemoveFromBlacklistCommand;
import com.messenger.friendsapp.domain.service.DomainBlacklistService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RemoveFromBlacklistCommandHandler {
    private final DomainBlacklistService domainBlacklistService;

    public RemoveFromBlacklistCommandHandler(DomainBlacklistService domainBlacklistService) {
        this.domainBlacklistService = domainBlacklistService;
    }

    /**
     * Removes blacklisted user from the blacklist
     * @param command CQS command for appropriate handler
     */
    public void handle(@NonNull RemoveFromBlacklistCommand command) {
        domainBlacklistService.removeFromBlacklist(command.getRequesterId(), command.getAddresseeId());
    }
}
