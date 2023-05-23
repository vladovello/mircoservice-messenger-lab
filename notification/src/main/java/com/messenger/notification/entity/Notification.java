package com.messenger.notification.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification {
    @Id
    @Column(nullable = false)
    @NonNull
    protected UUID id;
    @Column(nullable = false)
    @NonNull
    protected NotificationType notificationType;
    @Column(nullable = false)
    @NonNull
    protected String notificationMessage;
    @Column(nullable = false)
    @NonNull
    protected UUID userId;
    @Column
    protected LocalDateTime readingDate;
    @Column(nullable = false)
    @NonNull
    protected NotificationStatus status;
    @Column
    protected LocalDateTime receivingDate;

    protected Notification(
            @NonNull NotificationType notificationType,
            @NonNull String notificationMessage,
            @NonNull UUID userId
    ) {
        this.id = generateId();
        this.notificationType = notificationType;
        this.notificationMessage = notificationMessage;
        this.userId = userId;
        this.status = NotificationStatus.NOT_SEND;
        this.readingDate = null;
        this.receivingDate = null;
    }

    protected Notification() {
        // For JPA
    }

    public void markReceived() {
        this.receivingDate = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

    public void changeStatus(@NonNull NotificationStatus status) {
        switch (status) {
            case NOT_SEND:
                this.receivingDate = null;
                this.readingDate = null;
                break;
            case PENDING:
                if (this.status == NotificationStatus.VIEWED) {
                    this.receivingDate = LocalDateTime.now();
                }
                this.readingDate = null;
                break;
            case VIEWED:
                if (this.status == NotificationStatus.NOT_SEND) {
                    this.receivingDate = LocalDateTime.now();
                }
                this.readingDate = LocalDateTime.now();
                break;
        }

        this.status = status;
    }

    protected static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
