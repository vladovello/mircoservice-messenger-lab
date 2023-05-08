package com.messenger.chat.domain.chatparticipant.exception;

import lombok.NonNull;

public class UserAlreadyInChatException extends Exception {
    public <U, C> UserAlreadyInChatException(@NonNull U userId, @NonNull C chatId) {
        super(String.format(
                "User with id '%s' is already exists in chat with id '%s'",
                userId,
                chatId
        ));
    }
}
