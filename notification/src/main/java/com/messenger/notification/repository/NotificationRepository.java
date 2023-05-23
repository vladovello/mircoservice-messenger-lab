package com.messenger.notification.repository;

import com.messenger.notification.entity.Notification;
import com.messenger.notification.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>,
        PagingAndSortingRepository<Notification, UUID>,
        JpaSpecificationExecutor<Notification> {
    int countAllByUserIdAndStatusNotIn(UUID userId, Collection<NotificationStatus> statuses);
    boolean existsByIdAndUserId(UUID id, UUID userId);
}
