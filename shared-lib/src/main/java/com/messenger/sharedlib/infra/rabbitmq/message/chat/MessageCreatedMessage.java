package com.messenger.sharedlib.infra.rabbitmq.message.chat;

import com.messenger.sharedlib.domain.ddd.BaseDomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageCreatedMessage extends BaseDomainEvent {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID userId;
    private final String eventMessageText;

    public MessageCreatedMessage(UUID messageId, UUID chatId, UUID userId, String eventMessageText) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
        this.eventMessageText = eventMessageText;
    }
}
