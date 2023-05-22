package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.SynchronizeUserDataCommand;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SynchronizeUserDataCommandHandler {
    private final FriendshipRepository friendshipRepository;
    private final BlacklistRepository blacklistRepository;

    public SynchronizeUserDataCommandHandler(
            FriendshipRepository friendshipRepository,
            BlacklistRepository blacklistRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.blacklistRepository = blacklistRepository;
    }

    public void handle(@NonNull SynchronizeUserDataCommand command) {
        var fullName = new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName());
        friendshipRepository.updateFullNameById(command.getUserId(), fullName);
        blacklistRepository.updateFullNameById(command.getUserId(), fullName);
    }
}
