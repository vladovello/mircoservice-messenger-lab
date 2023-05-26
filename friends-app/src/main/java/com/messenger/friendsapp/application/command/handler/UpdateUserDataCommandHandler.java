package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.UpdateUserDataCommand;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserDataCommandHandler {
    private final FriendshipRepository friendshipRepository;
    private final BlacklistRepository blacklistRepository;

    public UpdateUserDataCommandHandler(
            FriendshipRepository friendshipRepository,
            BlacklistRepository blacklistRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.blacklistRepository = blacklistRepository;
    }

    /**
     * Updates user's data using both Friendship and Blacklist Repositories
     * @param command CQS command for appropriate handler
     */
    public void handle(@NonNull UpdateUserDataCommand command) {
        var fullName = new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName());
        friendshipRepository.updateFullNameById(command.getUserId(), fullName);
        blacklistRepository.updateFullNameById(command.getUserId(), fullName);
    }
}
