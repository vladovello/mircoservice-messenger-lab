package com.messenger.chat.infra.persistence.repository.jpa;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.infra.persistence.repository.impl.ChatIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatParticipantRepositoryJpa extends JpaRepository<ChatParticipant, UUID> {
    List<ChatParticipant> findAllByChatId(UUID chatId);

    List<ChatIdProjection> findAllByUserId(UUID userId);

    Optional<ChatParticipant> findByChatIdAndUserId(UUID chatId, UUID userId);

    boolean existsByChatIdAndUserId(UUID chatId, UUID userId);

    int countAllByChatId(UUID chatId);
}
