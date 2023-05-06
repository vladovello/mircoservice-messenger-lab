package com.messenger.chat.domain.chat.service.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.identity.AvatarId;

import java.util.UUID;

public class ChatDomainServiceImpl implements ChatDomainService {
    private final ChatRepository chatRepository;

    public ChatDomainServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createChat(AvatarId avatarId, ChatType chatType, ChatName chatName) {
        return chatRepository.save(Chat.createNew(avatarId, chatType, chatName));
    }

    @Override
    public Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName) {
        return chatRepository.save(Chat.createNew(new AvatarId(UUID.nameUUIDFromBytes("Ahahaha".getBytes())), chatType, chatName));
    }

    @Override
    public boolean changeChatAvatar(Chat chat, AvatarId avatarId) {
        return false;
    }
}
