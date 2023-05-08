package com.messenger.chat.domain.chatparticipant.repository;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;

import java.util.Optional;
import java.util.UUID;

public interface ChatParticipantRepository {
    Optional<ChatParticipant> getChatParticipant(UUID chatId, UUID userId);
    boolean isChatParticipantExists(UUID chatId, UUID userId);
    int getParticipantsCount(UUID chatId);
    void save(ChatParticipant chatParticipant);
    void delete(ChatParticipant chatParticipant);
}
