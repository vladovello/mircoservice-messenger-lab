package com.messenger.chat.domain.chatparticipant.exception;

import lombok.NonNull;

public class IllegalChatTypeException extends Exception {
    protected IllegalChatTypeException(String message) {
        super(message);
    }

    public static @NonNull IllegalChatTypeException ofDialogue() {
        return new IllegalChatTypeException("Cannot perform this operation with 'Dialogue' chat type");
    }

    public static @NonNull IllegalChatTypeException ofMulti() {
        return new IllegalChatTypeException("Cannot perform this operation with 'Multi' chat type");
    }
}
