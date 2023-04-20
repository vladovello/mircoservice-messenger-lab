package com.messenger.friendsapp.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteFriendCommand {
    private UUID requesterId;
    private UUID addresseeId;
}
