package com.messenger.chat.domain.chatparticipant.valueobject;

import com.messenger.chat.domain.chatparticipant.exception.InvalidChatRoleNameException;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;
import lombok.Value;

@Value
public class ChatRoleName {
    private static final int MAX_LENGTH = 100;

    String name;

    public static @NonNull Result<ChatRoleName> create(@NonNull String name) {
        if (name.isEmpty()) {
            return Result.failure(InvalidChatRoleNameException.emptyChatRoleNameException());
        } else if (name.length() > MAX_LENGTH) {
            return Result.failure(InvalidChatRoleNameException.tooLongChatRoleNameException(name.length(), MAX_LENGTH));
        }

        return Result.success(new ChatRoleName(name));
    }
}
