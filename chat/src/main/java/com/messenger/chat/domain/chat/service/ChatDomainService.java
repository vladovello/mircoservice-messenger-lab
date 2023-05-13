package com.messenger.chat.domain.chat.service;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;

import java.util.List;
import java.util.UUID;

// TODO: 13.05.2023 вынести методы, тесно связанные с чатом из других сервисов в этот
public interface ChatDomainService {
    Chat createMultiChat(ChatName chatName, UUID avatarId, UUID ownerId, List<UUID> participantIds);

    Chat createDialogueChat();

    Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName);

    boolean changeChatAvatar(Chat chat, UUID avatarId);
}
