package com.messenger.chat.domain.chat.exception;

import com.messenger.sharedlib.exception.NotFoundException;

import java.util.UUID;

public class ChatNotFoundException extends NotFoundException {
    public ChatNotFoundException(UUID chatId) {
        super(String.format("Chat with id '%s' is not found", chatId));
    }
}
