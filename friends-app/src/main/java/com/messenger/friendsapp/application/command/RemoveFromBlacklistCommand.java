package com.messenger.friendsapp.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class RemoveFromBlacklistCommand {
    private UUID requesterId;
    private UUID addresseeId;
}
