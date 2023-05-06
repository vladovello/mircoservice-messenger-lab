package com.messenger.chat.domain.chatparticipant;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.EnumSet;
import java.util.Set;

public enum Permission {
    DELETE_OTHERS_MESSAGES(1),
    DELETE_CHAT(1 << 1),
    INVITE_TO_CHAT(1 << 2),
    KICK_FROM_CHAT(1 << 3),
    GRANT_ROLE(1 << 4),
    TAKE_AWAY_ROLE(1 << 5),
    CREATE_ROLE(1 << 6),
    CHANGE_ROLE(1 << 6),
    DELETE_ROLE(1 << 6)
    ;

    @Getter private final int number;

    Permission(int number) {
        this.number = number;
    }

    @Contract(" -> new")
    public static @NonNull Set<Permission> getAll() {
        return EnumSet.allOf(Permission.class);
    }

    @Contract(" -> new")
    public static @NonNull Set<Permission> getNothing() {
        return EnumSet.noneOf(Permission.class);
    }
}
