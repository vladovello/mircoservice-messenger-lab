package com.messenger.chat.infra.persistence.repository.jpa;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ChatRepositoryJpa extends JpaRepository<Chat, UUID>, JpaSpecificationExecutor<Chat>,
        PagingAndSortingRepository<Chat, UUID> {
    ChatType findChatTypeById(UUID chatId);
    boolean existsById(@NonNull UUID chatId);
}
