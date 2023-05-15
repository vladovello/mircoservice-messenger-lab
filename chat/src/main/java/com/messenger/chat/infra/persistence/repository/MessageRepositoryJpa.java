package com.messenger.chat.infra.persistence.repository;

import com.messenger.chat.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepositoryJpa extends JpaRepository<Message, UUID>, JpaSpecificationExecutor<Message>,
        PagingAndSortingRepository<Message, UUID> {
    Optional<Message> findFirstByChatIdOrderByCreationDateDesc(UUID chatId);
}
