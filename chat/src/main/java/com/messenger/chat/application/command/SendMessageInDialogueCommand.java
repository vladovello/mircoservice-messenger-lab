package com.messenger.chat.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageInDialogueCommand {
    private UUID senderId;
    private UUID recipientId;
    private String messageText;
}
