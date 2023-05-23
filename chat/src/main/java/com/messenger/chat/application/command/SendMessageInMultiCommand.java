package com.messenger.chat.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageInMultiCommand {
    private UUID senderId;
    private UUID chatId;
    private String messageText;
}
