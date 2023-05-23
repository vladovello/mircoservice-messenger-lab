package com.messenger.notification.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class NotificationListDto {
    @NonNull private List<NotificationDto> notifications;
}
