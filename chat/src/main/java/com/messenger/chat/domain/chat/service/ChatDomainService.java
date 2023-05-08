package com.messenger.chat.domain.chat.service;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;

import java.util.UUID;

public interface ChatDomainService {
    Chat createChat(UUID avatarId, ChatType chatType, ChatName chatName);
    Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName);
    boolean changeChatAvatar(Chat chat, UUID avatarId);
}
