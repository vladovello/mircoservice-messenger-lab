package com.messenger.chat.domain.valueobject;

import com.messenger.chat.domain.exception.ChatNameIsToLongException;
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
        if (name.length() > MAX_LENGTH) {
            return Result.failure(new ChatNameIsToLongException(name.length(), MAX_LENGTH));
        }

        return Result.success(new ChatName(name));
    }
}
