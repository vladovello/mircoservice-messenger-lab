package com.messenger.notification.service.exception;

import java.util.UUID;

public class NotificationDoesNotBelongToUserException extends Exception {
    public NotificationDoesNotBelongToUserException(UUID notifId, UUID userId) {
        super(String.format("User with id '%s' has no access to notification with id '%s'", userId, notifId));
    }
}
