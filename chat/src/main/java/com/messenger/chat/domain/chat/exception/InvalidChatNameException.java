package com.messenger.chat.domain.chat.exception;

import com.messenger.chat.domain.exception.BusinessRuleViolationException;
import lombok.NonNull;

public class InvalidChatNameException extends BusinessRuleViolationException {
    public InvalidChatNameException(String message) {
        super(message);
    }

    public static @NonNull InvalidChatNameException tooLongChatRoleNameException(
            int currentChatRoleNameLength,
            int expectedChatRoleNameLength
    ) {
        return new InvalidChatNameException(String.format(
                "%d is to long for a chat name. Maximum is %d",
                currentChatRoleNameLength,
                expectedChatRoleNameLength
        ));
    }

    public static @NonNull InvalidChatNameException emptyChatRoleNameException() {
        return new InvalidChatNameException("Chat role name must not be empty");
    }
}
