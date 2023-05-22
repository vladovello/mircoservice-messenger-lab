package com.messenger.notification.entity;

import lombok.Getter;

public enum NotificationType {
    USER_LOGGED_IN("USER_LOGGED_IN"),
    MESSAGE_SENT("MESSAGE_SENT"),
    FRIENDSHIP_CREATED("FRIENDSHIP_CREATED"),
    FRIENDSHIP_REMOVED("FRIENDSHIP_REMOVED"),
    BLACKLIST_CREATED("BLACKLIST_CREATED"),
    BLACKLIST_REMOVED("BLACKLIST_REMOVED");

    @Getter
    private final String name;

    NotificationType(String name) {
        this.name = name;
    }
}
