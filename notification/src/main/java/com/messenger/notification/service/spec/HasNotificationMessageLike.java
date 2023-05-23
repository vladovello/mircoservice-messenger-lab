package com.messenger.notification.service.spec;

import com.messenger.notification.entity.Notification;
import com.messenger.notification.entity.NotificationFieldNames;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HasNotificationMessageLike implements Specification<Notification> {
    private final DiscreteParam<String> notificationMessage;

    public HasNotificationMessageLike(DiscreteParam<String> notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<Notification> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        if (notificationMessage != null && notificationMessage.getValue() != null) {
            return criteriaBuilder.like(
                    root.get(NotificationFieldNames.NOTIFICATION_MESSAGE),
                    "%" + notificationMessage.getValue() + "%"
            );
        }

        return criteriaBuilder.and();
    }
}
