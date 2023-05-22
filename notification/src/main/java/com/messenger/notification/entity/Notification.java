package com.messenger.notification.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Notification {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID id;
    @Column(nullable = false)
    @NonNull
    private NotificationType notificationType;
    @Column(nullable = false)
    @NonNull
    private String notificationMessage;
    @Column(nullable = false)
    @NonNull
    private UUID userId;
    @Column
    private LocalDateTime readingDate;
    @Column(nullable = false)
    @NonNull
    private NotificationStatus status;
    @Column
    private LocalDateTime receivingDate;

    protected Notification(
            @NonNull UUID id,
            @NonNull NotificationType notificationType,
            @NonNull String notificationMessage,
            @NonNull UUID userId,
            @NonNull NotificationStatus status
    ) {
        this.id = id;
        this.notificationType = notificationType;
        this.notificationMessage = notificationMessage;
        this.userId = userId;
        this.readingDate = null;
        this.status = status;
        this.receivingDate = null;
    }

    protected Notification() {
        // For JPA
    }

    public static @NonNull Notification createNew(
            NotificationType notificationType,
            String notificationMessage,
            UUID userId
    ) {
        return new Notification(
                generateId(),
                notificationType,
                notificationMessage,
                userId,
                NotificationStatus.NOT_SEND
        );
    }

    public static @NonNull Notification reconstruct(
            UUID notificationId,
            NotificationType notificationType,
            String notificationMessage,
            NotificationStatus notificationStatus,
            UUID userId
    ) {
        return new Notification(
                notificationId,
                notificationType,
                notificationMessage,
                userId,
                notificationStatus
        );
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
