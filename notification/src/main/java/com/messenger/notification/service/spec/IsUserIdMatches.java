package com.messenger.notification.service.spec;

import com.messenger.notification.entity.Notification;
import com.messenger.notification.entity.NotificationFieldNames;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class IsUserIdMatches implements Specification<Notification> {
    private final UUID userId;

    public IsUserIdMatches(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<Notification> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.equal(root.get(NotificationFieldNames.USER_ID), userId);
    }
}
