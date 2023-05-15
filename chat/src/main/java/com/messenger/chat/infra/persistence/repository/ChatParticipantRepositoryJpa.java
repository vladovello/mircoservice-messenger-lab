package com.messenger.chat.infra.persistence.repository;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatParticipantRepositoryJpa extends JpaRepository<ChatParticipant, UUID> {
    List<ChatParticipant> getAllByChatId(UUID chatId);
}
