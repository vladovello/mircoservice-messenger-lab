package com.messenger.chat.infra.persistence.repository.jpa;

import com.messenger.chat.domain.chat.Chat;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ChatRepositoryJpa extends JpaRepository<Chat, UUID>, JpaSpecificationExecutor<Chat>,
        PagingAndSortingRepository<Chat, UUID> {
    boolean existsById(@NonNull UUID chatId);
}
