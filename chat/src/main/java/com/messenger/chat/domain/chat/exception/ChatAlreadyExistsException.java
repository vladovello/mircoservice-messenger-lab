package com.messenger.chat.domain.chat.exception;

import com.messenger.sharedlib.exception.AlreadyExistsException;

import java.util.UUID;

public class ChatAlreadyExistsException extends AlreadyExistsException {
    public ChatAlreadyExistsException(UUID chatId) {
        super(String.format("Chat with id '%s' is already exists", chatId));
    }

    public ChatAlreadyExistsException(UUID userId1, UUID userId2) {
        super(String.format("Dialogue between users with ids '%s' and '%s' is already exists", userId1, userId2));
    }
}
