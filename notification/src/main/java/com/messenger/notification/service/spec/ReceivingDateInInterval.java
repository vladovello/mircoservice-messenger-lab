package com.messenger.notification.service.spec;

import com.messenger.notification.entity.Notification;
import com.messenger.notification.entity.NotificationFieldNames;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReceivingDateInInterval implements Specification<Notification> {
    private final IntervalParam<LocalDate> receivingDateInterval;

    public ReceivingDateInInterval(IntervalParam<LocalDate> receivingDateInterval) {
        this.receivingDateInterval = receivingDateInterval;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<Notification> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        if (receivingDateInterval == null || receivingDateInterval.getInterval() == null) {
            return criteriaBuilder.and();
        }

        var interval = receivingDateInterval.getInterval();

        if (!interval.hasLowerBound()) {
            return criteriaBuilder.between(
                    root.get(NotificationFieldNames.RECEIVING_DATE),
                    LocalDateTime.MIN,
                    LocalDateTime.of(interval.upperEndpoint(), LocalTime.MAX)
            );
        } else if (!interval.hasUpperBound()) {
            return criteriaBuilder.between(
                    root.get(NotificationFieldNames.RECEIVING_DATE),
                    LocalDateTime.of(interval.lowerEndpoint(), LocalTime.MIN),
                    LocalDateTime.MAX
            );
        }

        return criteriaBuilder.between(
                root.get(NotificationFieldNames.RECEIVING_DATE),
                LocalDateTime.of(interval.lowerEndpoint(), LocalTime.MIN),
                LocalDateTime.of(interval.upperEndpoint(), LocalTime.MAX)
        );
    }
}
