package com.messenger.chat.domain.chat.service.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.chatparticipant.service.ChatParticipantDomainService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

// TODO: 13.05.2023 think about creating ChatManagingDomainService instead of Chat and ChatParticipant Domain Services
public class ChatDomainServiceImpl implements ChatDomainService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantDomainService chatParticipantDomainService;
    private final ChatRepository chatRepository;

    public ChatDomainServiceImpl(
            ChatParticipantRepository chatParticipantRepository,
            ChatParticipantDomainService chatParticipantDomainService,
            ChatRepository chatRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.chatParticipantDomainService = chatParticipantDomainService;
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createMultiChat(ChatName chatName, UUID ownerId, UUID avatarId, @NonNull List<UUID> participantIds) {
        var chat = Chat.createMulti(avatarId, chatName);
        var owner = ChatParticipant.createNew(ownerId, chat.getId(), chat.getOwnerRole().get(), chat.getEveryoneRole());

        for (var participantId : participantIds) {
            chatParticipantDomainService.addUserToMultiChat(owner, participantId);
        }

        // INFO: maybe this saves is redundant
        chatParticipantRepository.save(owner);
        chatRepository.save(chat);

        return chat;
    }

    @Override
    public Chat createDialogueChat() {
        // TODO: 13.05.2023 generate default avatar
        return Chat.createDialogue(UUID.randomUUID());
    }

    @Override
    public Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName) {
        return null;
    }

    @Override
    public boolean changeChatAvatar(Chat chat, UUID avatarId) {
        return false;
    }
}
