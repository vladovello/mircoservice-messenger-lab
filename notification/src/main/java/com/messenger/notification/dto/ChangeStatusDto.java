package com.messenger.notification.dto;

import com.messenger.notification.entity.NotificationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChangeStatusDto {
    private List<UUID> notificationIds;
    private NotificationStatus notificationStatus;
}
