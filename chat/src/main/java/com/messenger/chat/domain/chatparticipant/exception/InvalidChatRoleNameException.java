package com.messenger.chat.domain.chatparticipant.exception;

import com.messenger.chat.domain.exception.BusinessRuleViolationException;
import lombok.NonNull;

public class InvalidChatRoleNameException extends BusinessRuleViolationException {
    public InvalidChatRoleNameException(String message) {
        super(message);
    }

    public static @NonNull InvalidChatRoleNameException tooLongChatRoleNameException(
            int currentChatRoleNameLength,
            int expectedChatRoleNameLength
    ) {
        return new InvalidChatRoleNameException(String.format(
                "%d is to long for a chat name. Maximum is %d",
                currentChatRoleNameLength,
                expectedChatRoleNameLength
        ));
    }

    public static @NonNull InvalidChatRoleNameException emptyChatRoleNameException() {
        return new InvalidChatRoleNameException("Chat role name must not be empty");
    }
}
