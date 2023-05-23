package com.messenger.chat.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageInDialogueCommand {
    private UUID senderId;
    private UUID recipientId;
    private String messageText;
}
