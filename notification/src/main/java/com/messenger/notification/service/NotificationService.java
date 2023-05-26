package com.messenger.notification.service;

import com.messenger.notification.dto.NotificationDto;
import com.messenger.notification.entity.NotificationStatus;
import com.messenger.notification.entity.NotificationType;
import com.messenger.notification.service.exception.NotificationDoesNotBelongToUserException;
import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    /**
     * @param pageNumber number of a requested page
     * @param pageSize size of a requested page
     * @param userId id of the requester
     * @param receivingDateDto date search parameter
     * @param notificationTypesDto {@code NotificationType} search parameter
     * @param notificationMessageDto parameter for searching by notification message
     * @return list of representations of {@code Notification}
     */
    List<NotificationDto> getNotificationsPaginatedWithParams(
            int pageNumber,
            int pageSize,
            @NonNull UUID userId,
            IntervalParamDto<LocalDate> receivingDateDto,
            DiscreteParamDto<List<NotificationType>> notificationTypesDto,
            DiscreteParamDto<String> notificationMessageDto
    );

    /**
     * @param userId id of the user
     * @return number of unread notifications of the specified user
     */
    int getUnreadCount(UUID userId);

    /**
     * @param userId             id of the user
     * @param notificationIds    notification ids to change status to
     * @param notificationStatus new notifications status
     * @throws NotificationDoesNotBelongToUserException thrown if notification listed doesn't belong to user with {@code userId}
     */
    void changeStatus(
            UUID userId,
            @NonNull List<UUID> notificationIds,
            NotificationStatus notificationStatus
    ) throws NotificationDoesNotBelongToUserException;
}
