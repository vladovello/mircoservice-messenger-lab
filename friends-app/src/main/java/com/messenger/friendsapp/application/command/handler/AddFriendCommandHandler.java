package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.AddFriendCommand;
import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;
import com.messenger.friendsapp.domain.service.DomainFriendshipService;
import com.messenger.friendsapp.domain.valueobject.FullName;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AddFriendCommandHandler {
    private final DomainFriendshipService domainFriendshipService;

    public AddFriendCommandHandler(DomainFriendshipService domainFriendshipService) {
        this.domainFriendshipService = domainFriendshipService;
    }

    /**
     * Adds friend to requester's friend list
     * @param command CQS command for appropriate handler
     */
    public void handle(@NonNull AddFriendCommand command) throws UserIsBlockedException {
        domainFriendshipService.addToFriendshipList(
                command.getRequesterId(),
                Addressee.createAddressee(
                        command.getAddresseeId(),
                        new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName())
                )
        );
    }
}
