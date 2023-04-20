package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.AddToBlacklistCommand;
import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.service.DomainBlacklistService;
import com.messenger.friendsapp.domain.valueobject.FullName;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AddToBlacklistCommandHandler {
    private final DomainBlacklistService domainBlacklistService;

    public AddToBlacklistCommandHandler(DomainBlacklistService domainBlacklistService) {
        this.domainBlacklistService = domainBlacklistService;
    }

    public void handle(@NonNull AddToBlacklistCommand command) {
        domainBlacklistService.addToBlacklist(
                command.getRequesterId(),
                Addressee.createAddressee(
                        command.getAddresseeId(),
                        new FullName(
                                command.getFirstName(),
                                command.getMiddleName(),
                                command.getLastName()
                        )
                )
        );
    }
}
