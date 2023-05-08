package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import lombok.NonNull;

import java.util.UUID;

public class RoleFactory {
    private RoleFactory() {
    }

    public static @NonNull ChatRole createOwner(UUID chatId) {
        return ChatRole.createNew(
                chatId,
                ChatRoleName.createOwnerName(),
                Permission.getAll(),
                ChatRole.HIGHEST_PRIORITY
        );
    }

    public static @NonNull ChatRole createEveryone(UUID chatId) {
        return ChatRole.createNew(
                chatId,
                ChatRoleName.createEveryoneName(),
                Permission.getNothing(),
                ChatRole.LOWEST_PRIORITY
        );
    }
}
