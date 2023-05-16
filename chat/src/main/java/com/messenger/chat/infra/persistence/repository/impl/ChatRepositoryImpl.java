package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.infra.persistence.repository.jpa.ChatParticipantRepositoryJpa;
import com.messenger.chat.infra.persistence.repository.jpa.ChatRepositoryJpa;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        var optionalDialogueChat = chats1.stream()
                .filter(chats2::contains)
                .filter(chatIdProjection -> getChatType(chatIdProjection.getChatId()) == ChatType.DIALOGUE)
                .findFirst();

        return optionalDialogueChat.map(chatIdProjection -> chatRepositoryJpa.findById(
                        chatIdProjection.getChatId()
                ).get()
        );
    }

    @Override
    public ChatType getChatType(UUID chatId) {
        return chatRepositoryJpa.findChatTypeById(chatId);
    }

    @Override
    public boolean isChatExists(UUID chatId) {
        return chatRepositoryJpa.existsById(chatId);
    }

    @Override
    public boolean isDialogueExists(UUID userId1, UUID userId2) {
        var chats1 = chatParticipantRepositoryJpa.findAllByUserId(userId1);
        var chats2 = chatParticipantRepositoryJpa.findAllByUserId(userId2);

        // TODO: 16.05.2023 maybe rewrite to query
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
