package com.messenger.notification.entity;

import lombok.NonNull;

import java.util.UUID;

public class NotificationFactory {
    private NotificationFactory() {
    }

    public static @NonNull Notification userLoggedNotification(@NonNull UUID userId) {
        return Notification.createNew(
                NotificationType.USER_LOGGED_IN,
                "Someone logged in your account",
                userId
        );
    }

    public static @NonNull Notification friendshipCreatedNotification(@NonNull UUID userId, String fullName) {
        return Notification.createNew(
                NotificationType.USER_LOGGED_IN,
                String.format("The user '%s' has added you to his friend list", fullName),
                userId
        );
    }

    public static @NonNull Notification friendshipDeletedNotification(@NonNull UUID userId, String fullName) {
        return Notification.createNew(
                NotificationType.FRIENDSHIP_CREATED,
                String.format("The user '%s' has removed you from his friend list", fullName),
                userId
        );
    }

    public static @NonNull Notification blacklistItemCreatedMessage(@NonNull UUID userId, String fullName) {
        return Notification.createNew(
                NotificationType.BLACKLIST_CREATED,
                String.format("The user '%s' has added you to his blacklist", fullName),
                userId
        );
    }

    public static @NonNull Notification blacklistItemDeletedMessage(@NonNull UUID userId, String fullName) {
        return Notification.createNew(
                NotificationType.BLACKLIST_REMOVED,
                String.format("The user '%s' has removed you from his blacklist", fullName),
                userId
        );
    }
}
