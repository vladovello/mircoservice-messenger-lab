package com.messenger.notification.service.spec;

import com.messenger.notification.entity.Notification;
import com.messenger.notification.entity.NotificationFieldNames;
import com.messenger.notification.entity.NotificationType;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class HasOneOfNotificationTypes implements Specification<Notification> {
    private final DiscreteParam<List<NotificationType>> notificationTypes;

    public HasOneOfNotificationTypes(DiscreteParam<List<NotificationType>> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Notification> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        if (notificationTypes != null && notificationTypes.getValue() != null) {
            return root.get(NotificationFieldNames.NOTIFICATION_TYPE).in(notificationTypes.getValue());
        }

        return criteriaBuilder.and();
    }
}
