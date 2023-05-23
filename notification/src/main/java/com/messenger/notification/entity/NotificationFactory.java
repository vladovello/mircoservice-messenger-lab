package com.messenger.notification.entity;

import lombok.NonNull;

import java.util.UUID;

public class NotificationFactory {
    private NotificationFactory() {
    }

    public static @NonNull Notification userLoggedNotification(@NonNull UUID userId) {
        return new Notification(
                NotificationType.USER_LOGGED_IN,
                "Someone logged in your account",
                userId
        );
    }

    public static @NonNull Notification friendshipCreatedNotification(@NonNull UUID userId, String fullName) {
        return new Notification(
                NotificationType.USER_LOGGED_IN,
                String.format("The user '%s' has added you to his friend list", fullName),
                userId
        );
    }

    public static @NonNull Notification friendshipDeletedNotification(@NonNull UUID userId, String fullName) {
        return new Notification(
                NotificationType.FRIENDSHIP_CREATED,
                String.format("The user '%s' has removed you from his friend list", fullName),
                userId
        );
    }

    public static @NonNull Notification blacklistItemCreatedNotification(@NonNull UUID userId, String fullName) {
        return new Notification(
                NotificationType.BLACKLIST_CREATED,
                String.format("The user '%s' has added you to his blacklist", fullName),
                userId
        );
    }

    public static @NonNull Notification blacklistItemDeletedNotification(@NonNull UUID userId, String fullName) {
        return new Notification(
                NotificationType.BLACKLIST_REMOVED,
                String.format("The user '%s' has removed you from his blacklist", fullName),
                userId
        );
    }

    public static @NonNull Notification messageSentNotification(
            @NonNull UUID userId,
            @NonNull UUID chatId,
            @NonNull UUID messageId,
            @NonNull String messageText
    ) {
        return new MessageNotification(
                messageText,
                userId,
                chatId,
                messageId
        );
    }
}
