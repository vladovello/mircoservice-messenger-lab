package com.messenger.chat.domain.message.event;

import com.messenger.sharedlib.ddd.domain.DomainEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MessageCreatedDomainEvent implements DomainEvent {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID userId;

    public MessageCreatedDomainEvent(UUID messageId, UUID chatId, UUID userId) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
    }

    @Override
    public LocalDateTime getCompletionTime() {
        return null;
    }
}
