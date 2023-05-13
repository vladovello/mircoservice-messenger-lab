package com.messenger.chat.application.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChangeMultiChatCommand {
    private UUID actorId;
    private UUID id;
    private String chatName;
    private UUID avatarId;
    private List<UUID> usersList;
}
