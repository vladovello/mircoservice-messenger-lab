package com.messenger.chat.domain.chatparticipant.exception;

import com.messenger.sharedlib.exception.NotFoundException;

import java.util.UUID;

public class ChatParticipantNotFoundException extends NotFoundException {
    public ChatParticipantNotFoundException(UUID chatId, UUID userId) {
        super(String.format("User with id '%s' in chat with id '%s' is not found", userId, chatId));
    }
}
