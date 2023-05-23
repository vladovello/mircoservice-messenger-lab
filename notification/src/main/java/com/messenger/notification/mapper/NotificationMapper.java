package com.messenger.notification.mapper;

import com.messenger.notification.dto.NotificationDto;
import com.messenger.notification.entity.Notification;
import lombok.NonNull;

public class NotificationMapper {
    private NotificationMapper() {
    }

    public static NotificationDto mapToDto(@NonNull Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getNotificationType(),
                notification.getNotificationMessage(),
                notification.getStatus(),
                notification.getReceivingDate()
        );
    }
}
