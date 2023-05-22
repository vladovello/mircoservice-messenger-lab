package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.DeleteFriendCommand;
import com.messenger.friendsapp.domain.service.DomainBlacklistService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RemoveFromBlacklistCommandHandler {
    private final DomainBlacklistService domainBlacklistService;

    public RemoveFromBlacklistCommandHandler(DomainBlacklistService domainBlacklistService) {
        this.domainBlacklistService = domainBlacklistService;
    }

    public void handle(@NonNull DeleteFriendCommand command) {
        domainBlacklistService.removeFromBlacklist(command.getRequesterId(), command.getAddresseeId());
    }
}
