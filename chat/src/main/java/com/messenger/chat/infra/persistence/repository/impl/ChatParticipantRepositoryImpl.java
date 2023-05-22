package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.infra.persistence.repository.jpa.ChatParticipantRepositoryJpa;
import com.messenger.chat.infra.persistence.repository.jpa.ChatRepositoryJpa;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatParticipantRepositoryImpl implements ChatParticipantRepository {
    private final ChatParticipantRepositoryJpa chatParticipantRepositoryJpa;
    private final ChatRepositoryJpa chatRepositoryJpa;

    public ChatParticipantRepositoryImpl(
            ChatParticipantRepositoryJpa chatParticipantRepositoryJpa,
            ChatRepositoryJpa chatRepositoryJpa
    ) {
        this.chatParticipantRepositoryJpa = chatParticipantRepositoryJpa;
        this.chatRepositoryJpa = chatRepositoryJpa;
    }

    @Override
    public Optional<ChatParticipant> getChatParticipant(UUID chatId, UUID userId) {
        return chatParticipantRepositoryJpa.findByChatIdAndUserId(chatId, userId);
    }

    @Override
    public List<ChatParticipant> getAllByChatId(UUID chatId) {
        return chatParticipantRepositoryJpa.findAllByChatId(chatId);
    }

    @Override
    public Optional<ChatParticipant> getChatOwner(UUID chatId) {
        var optionalChat = chatRepositoryJpa.findById(chatId);

        if (optionalChat.isEmpty()) return Optional.empty();

        var chat = optionalChat.get();

        var optionalOwnerRole = chat.getOwnerRole();

        if (optionalOwnerRole.isEmpty()) return Optional.empty();

        var ownerRole = optionalOwnerRole.get();

        // TODO: 19.05.2023 I don't know how to create this query with JPA. I'm to low on time, so I'm writing this shit. I feel sorry...
        var participants = chatParticipantRepositoryJpa.findAllByChatId(chatId);

        return participants.stream()
                .filter(chatParticipant -> chatParticipant.getChatRoles().contains(ownerRole))
                .findFirst();
    }

    @Override
    public boolean isUserBelongsToChat(UUID chatId, UUID userId) {
        return chatParticipantRepositoryJpa.existsByChatIdAndUserId(chatId, userId);
    }

    @Override
    public int getParticipantsCount(UUID chatId) {
        return chatParticipantRepositoryJpa.countAllByChatId(chatId);
    }

    @Override
    public void save(ChatParticipant chatParticipant) {
        chatParticipantRepositoryJpa.save(chatParticipant);
    }

    @Override
    public void delete(@NonNull ChatParticipant chatParticipant) {
        chatParticipantRepositoryJpa.deleteById(chatParticipant.getChatParticipantId());
    }
}
