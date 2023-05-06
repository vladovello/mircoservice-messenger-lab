package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import com.messenger.chat.domain.identity.ChatId;

public class RoleFactory {
    public ChatRole createAdmin(ChatId chatId, ChatRoleName chatRoleName) {
        return ChatRole.createNew(chatId, chatRoleName, Permission.getAll());
    }

    public ChatRole createEveryone(ChatId chatId, ChatRoleName chatRoleName) {
        return ChatRole.createNew(chatId, chatRoleName, Permission.getNothing());
    }
}
