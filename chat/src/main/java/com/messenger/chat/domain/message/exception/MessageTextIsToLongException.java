package com.messenger.chat.domain.message.exception;

import com.messenger.chat.domain.exception.BusinessRuleViolationException;

public class MessageTextIsToLongException extends BusinessRuleViolationException {
    public MessageTextIsToLongException(int currentMessageTextLength, int expectedMessageTextLength) {
        super(String.format(
                "%d is to long for a chat name. Maximum is %d",
                currentMessageTextLength,
                expectedMessageTextLength
        ));
    }
}
