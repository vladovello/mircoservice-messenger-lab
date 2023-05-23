package com.messenger.notification.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class MessageNotification extends Notification {
    @Column(nullable = false)
    @NonNull
    private UUID chatId;
    @Column(nullable = false)
    @NonNull
    private UUID messageId;

    protected MessageNotification() {
        // For JPA
    }

    protected MessageNotification(
            @NonNull String notificationMessage,
            @NonNull UUID userId,
            @NonNull UUID chatId,
            @NonNull UUID messageId
    ) {
        super(NotificationType.MESSAGE_SENT, notificationMessage, userId);
        this.chatId = chatId;
        this.messageId = messageId;
    }
}
