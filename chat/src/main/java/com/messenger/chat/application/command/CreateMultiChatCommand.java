package com.messenger.chat.application.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateMultiChatCommand {
    private UUID ownerId;
    private String chatName;
    private UUID avatarId;
    private List<UUID> usersList;
}
