package com.messenger.notification.service;

import com.messenger.notification.dto.NotificationDto;
import com.messenger.notification.entity.NotificationFieldNames;
import com.messenger.notification.entity.NotificationStatus;
import com.messenger.notification.entity.NotificationType;
import com.messenger.notification.mapper.NotificationMapper;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.notification.service.exception.NotificationDoesNotBelongToUserException;
import com.messenger.notification.service.spec.HasNotificationMessageLike;
import com.messenger.notification.service.spec.HasOneOfNotificationTypes;
import com.messenger.notification.service.spec.IsUserIdMatches;
import com.messenger.notification.service.spec.ReceivingDateInInterval;
import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import com.messenger.sharedlib.parameter.order.SortingOrder;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void errorTest() {
        String s = null;
        s.length();
    }

    @Transactional
    public List<NotificationDto> getNotificationsPaginatedWithParams(
            int pageNumber,
            int pageSize,
            @NonNull UUID userId,
            IntervalParamDto<LocalDate> receivingDateDto,
            DiscreteParamDto<List<NotificationType>> notificationTypesDto,
            DiscreteParamDto<String> notificationMessageDto
    ) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        var receivingDate = IntervalParam.fromDto(receivingDateDto);
        var notificationTypes = DiscreteParam.fromDto(notificationTypesDto);
        var notificationMessage = DiscreteParam.fromDto(notificationMessageDto);

        var pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                getSortingByParams(receivingDate, notificationTypes, notificationMessage)
        );

        var pageNotifications = notificationRepository.findAll(
                new IsUserIdMatches(userId)
                        .and(new ReceivingDateInInterval(receivingDate))
                        .and(new HasOneOfNotificationTypes(notificationTypes))
                        .and(new HasNotificationMessageLike(notificationMessage)),
                pageRequest
        );

        return pageNotifications.map(notification -> {
            notification.markReceived();
            return NotificationMapper.mapToDto(notification);
        }).getContent();
    }

    public int getUnreadCount(UUID userId) {
        return notificationRepository.countAllByUserIdAndStatusNotIn(userId, List.of(NotificationStatus.VIEWED));
    }

    public int changeStatus(
            UUID userId,
            @NonNull List<UUID> notificationIds,
            NotificationStatus notificationStatus
    ) throws NotificationDoesNotBelongToUserException {
        for (var id : notificationIds) {
            if (!notificationRepository.existsByIdAndUserId(id, userId)) {
                throw new NotificationDoesNotBelongToUserException(id, userId);
            }
        }

        var notifications = notificationRepository.findAllById(notificationIds);

        for (var notif : notifications) {
            notif.changeStatus(notificationStatus);
        }

        notificationRepository.saveAll(notifications);

        return getUnreadCount(userId);
    }

    private static Sort getSortingByParams(
            IntervalParam<LocalDate> receivingDate,
            DiscreteParam<List<NotificationType>> notificationTypes,
            DiscreteParam<String> notificationMessage
    ) {
        var sort = Sort.unsorted();

        if (receivingDate != null) {
            sort = sort.and(getSortParam(NotificationFieldNames.RECEIVING_DATE, receivingDate.getSortingOrder()));
        }
        if (notificationTypes != null) {
            sort = sort.and(getSortParam(
                    NotificationFieldNames.NOTIFICATION_TYPE,
                    notificationTypes.getSortingOrder()
            ));
        }
        if (notificationMessage != null) {
            sort = sort.and(getSortParam(
                    NotificationFieldNames.NOTIFICATION_MESSAGE,
                    notificationMessage.getSortingOrder()
            ));
        }

        return sort;
    }

    private static @NonNull Sort getSortParam(String propertyName, @NonNull SortingOrder sortingOrder) {
        switch (sortingOrder) {
            case ASC:
                return Sort.by(Sort.Direction.ASC, propertyName);
            case DESC:
                return Sort.by(Sort.Direction.DESC, propertyName);
            default:
                throw new AssertionError(sortingOrder);
        }
    }
}
