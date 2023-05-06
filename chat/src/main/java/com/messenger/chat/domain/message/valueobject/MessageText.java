package com.messenger.chat.domain.message.valueobject;

import com.messenger.chat.domain.message.exception.MessageTextIsToLongException;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;
import lombok.Value;

@Value
public class MessageText {
    private static final int MAX_LENGTH = 1000;

    String text;

    public static @NonNull Result<MessageText> create(@NonNull String text) {
        if (text.length() > MAX_LENGTH) {
            return Result.failure(new MessageTextIsToLongException(text.length(), MAX_LENGTH));
        }

        return Result.success(new MessageText(text));
    }
}
