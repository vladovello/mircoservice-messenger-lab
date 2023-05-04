package com.messenger.chat.domain.exception;

public class ChatNameIsToLongException extends BusinessRuleViolationException {
    public ChatNameIsToLongException(int currentChatNameLength, int expectedChatNameLength) {
        super(String.format(
                "%d is to long for a chat name. Maximum is %d",
                currentChatNameLength,
                expectedChatNameLength
        ));
    }
}
