package com.messenger.chat.domain.chat.service;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.identity.AvatarId;

public interface ChatDomainService {
    Chat createChat(AvatarId avatarId, ChatType chatType, ChatName chatName);
    Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName);
    boolean changeChatAvatar(Chat chat, AvatarId avatarId);
}
