package com.messenger.notification.dto;

import com.messenger.notification.entity.NotificationStatus;
import com.messenger.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NotificationDto {
    private UUID id;
    private NotificationType notificationType;
    private String notificationMessage;
    private NotificationStatus status;
    private LocalDateTime receivingDate;
}
