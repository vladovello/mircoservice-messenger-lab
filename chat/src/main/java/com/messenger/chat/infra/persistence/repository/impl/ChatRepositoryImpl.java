package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.infra.persistence.repository.jpa.ChatParticipantRepositoryJpa;
import com.messenger.chat.infra.persistence.repository.jpa.ChatRepositoryJpa;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatRepositoryJpa chatRepositoryJpa;
    private final ChatParticipantRepositoryJpa chatParticipantRepositoryJpa;

    public ChatRepositoryImpl(
            ChatRepositoryJpa chatRepositoryJpa,
            ChatParticipantRepositoryJpa chatParticipantRepositoryJpa
    ) {
        this.chatRepositoryJpa = chatRepositoryJpa;
        this.chatParticipantRepositoryJpa = chatParticipantRepositoryJpa;
    }

    @Override
    public Optional<Chat> getChat(UUID chatId) {
        return chatRepositoryJpa.findById(chatId);
    }

    @Override
    public Optional<Chat> getDialogue(UUID userId1, UUID userId2) {
        var chats1 = chatParticipantRepositoryJpa.findAllByUserId(userId1);
        var chats2 = chatParticipantRepositoryJpa.findAllByUserId(userId2);

        var optionalChatIdProj = chats1.stream()
                .filter(chats1IdProj -> chats2
                        .stream()
                        .anyMatch(chats2IdProj -> chats1IdProj.getChatId().equals(chats2IdProj.getChatId()))
                )
                .filter(chatIdProjection -> getChatType(chatIdProjection.getChatId()) == ChatType.DIALOGUE)
                .findFirst();

        return optionalChatIdProj.map(chatIdProjection -> chatRepositoryJpa.findById(
                        chatIdProjection.getChatId()
                ).get()
        );
    }

    @Override
    public ChatType getChatType(UUID chatId) {
        var optionalChat = chatRepositoryJpa.findById(chatId);
        return optionalChat.map(Chat::getChatType).orElse(ChatType.UNKNOWN);
    }

    @Override
    public boolean isChatExists(UUID chatId) {
        return chatRepositoryJpa.existsById(chatId);
    }

    @Override
    public boolean isDialogueExists(UUID userId1, UUID userId2) {
        var chats1 = chatParticipantRepositoryJpa.findAllByUserId(userId1);
        var chats2 = chatParticipantRepositoryJpa.findAllByUserId(userId2);

        // TODO: 16.05.2023 maybe rewrite to JPQL
        var optionalDialogueChat = chats1.stream()
                .filter(chats2::contains)
                .filter(chatIdProjection -> getChatType(chatIdProjection.getChatId()) == ChatType.DIALOGUE)
                .findFirst();

        return optionalDialogueChat.isPresent();
    }

    @Override
    public void save(@NonNull Chat chat) {
        chatRepositoryJpa.save(chat);
    }

    private static @NonNull Specification<List<Chat>> hasCommonChats(UUID userId1, UUID userId2) {
        return (root, query, criteriaBuilder) -> {
            Join<Chat, ChatParticipant> join = root.join("chatParticipant");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(join.get("userId"), userId1),
                    criteriaBuilder.equal(join.get("userId"), userId2),
                    criteriaBuilder.equal(join.get("chatId"), join.get("chatId"))
            );
        };
    }
}
