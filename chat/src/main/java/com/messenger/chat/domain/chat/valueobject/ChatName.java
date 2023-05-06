package com.messenger.chat.domain.chat.valueobject;

import com.messenger.chat.domain.chat.exception.InvalidChatNameException;
import com.messenger.chat.domain.chatparticipant.exception.InvalidChatRoleNameException;
import com.messenger.sharedlib.util.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatName {
    private static final int MAX_LENGTH = 100;

    String name;

    public static Result<ChatName> create(@NonNull String name) {
        if (name.isEmpty()) {
            return Result.failure(InvalidChatRoleNameException.emptyChatRoleNameException());
        } else if (name.length() > MAX_LENGTH) {
            return Result.failure(InvalidChatNameException.tooLongChatRoleNameException(name.length(), MAX_LENGTH));
        }

        return Result.success(new ChatName(name));
    }
}
