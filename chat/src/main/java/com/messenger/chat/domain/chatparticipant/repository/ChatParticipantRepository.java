package com.messenger.chat.domain.chatparticipant.repository;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatParticipantRepository {
    Optional<ChatParticipant> getChatParticipant(UUID chatId, UUID userId);
    List<ChatParticipant> getAllByChatId(UUID chatId);
    Optional<ChatParticipant> getChatOwner(UUID chatId);
    boolean isUserBelongsToChat(UUID chatId, UUID userId);
    int getParticipantsCount(UUID chatId);
    void save(ChatParticipant chatParticipant);
    void delete(ChatParticipant chatParticipant);
}
