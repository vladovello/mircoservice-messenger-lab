package com.messenger.chat.domain.message.event;

import com.messenger.chat.domain.message.valueobject.EventMessageText;
import com.messenger.sharedlib.domain.ddd.BaseDomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageCreatedDomainEvent extends BaseDomainEvent {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID userId;
    private final EventMessageText eventMessageText;

    public MessageCreatedDomainEvent(UUID messageId, UUID chatId, UUID userId, EventMessageText eventMessageText) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
        this.eventMessageText = eventMessageText;
    }
}
