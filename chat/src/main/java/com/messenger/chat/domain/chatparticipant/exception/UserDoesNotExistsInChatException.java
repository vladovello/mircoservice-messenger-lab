package com.messenger.chat.domain.chatparticipant.exception;

import lombok.NonNull;

public class UserDoesNotExistsInChatException extends Exception {
    public <U, C> UserDoesNotExistsInChatException(@NonNull U userId, @NonNull C chatId) {
        super(String.format("The user with id '%s' doesn't belong to the chat with id '%s'", userId, chatId));
    }
}
