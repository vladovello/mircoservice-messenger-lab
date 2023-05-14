package com.messenger.chat.application.command;

import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageInMultiCommand {
    private UUID senderId;
    private UUID chatId;
    private String messageText;
}
