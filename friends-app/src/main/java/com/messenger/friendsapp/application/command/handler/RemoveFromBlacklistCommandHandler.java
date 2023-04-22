package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.DeleteFriendCommand;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RemoveFromBlacklistCommandHandler {
    private final BlacklistRepository blacklistRepository;

    public RemoveFromBlacklistCommandHandler(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public void handle(@NonNull DeleteFriendCommand command) {
        blacklistRepository.delete(command.getRequesterId(), command.getAddresseeId());
    }
}
