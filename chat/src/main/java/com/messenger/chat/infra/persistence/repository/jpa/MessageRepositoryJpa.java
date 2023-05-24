package com.messenger.chat.infra.persistence.repository.jpa;

import com.messenger.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepositoryJpa extends JpaRepository<Message, UUID>, JpaSpecificationExecutor<Message>,
        PagingAndSortingRepository<Message, UUID> {

    Optional<Message> findFirstByChatIdOrderByCreationDateDesc(UUID chatId);
    Page<Message> findAllByChatId(Pageable pageable, UUID chatId);
    List<Message> findAllByChatIdAndCreationDateBetween(UUID chatId, LocalDateTime creationDateStart, LocalDateTime creationDateEnd);
}
