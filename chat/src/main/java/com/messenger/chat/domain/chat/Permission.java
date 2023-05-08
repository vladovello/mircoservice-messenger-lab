package com.messenger.chat.domain.chat;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.*;

public enum Permission {
    DELETE_OTHERS_MESSAGES(1),
    DELETE_CHAT(1 << 1),
    INVITE_TO_CHAT(1 << 2),
    KICK_FROM_CHAT(1 << 3),
    GRANT_ROLE(1 << 4),
    TAKE_AWAY_ROLE(1 << 5),
    CREATE_ROLE(1 << 6),
    CHANGE_ROLE(1 << 7),
    DELETE_ROLE(1 << 8);

    @Getter private final int code;
    private static final Map<Integer, Permission> BY_CODE = new HashMap<>();

    static {
        for (Permission e : values()) {
            BY_CODE.put(e.getCode(), e);
        }
    }

    public static Permission valueOfLabel(Integer code) {
        return BY_CODE.get(code);
    }

    Permission(int code) {
        this.code = code;
    }

    @Contract(" -> new")
    public static @NonNull Set<Permission> getAll() {
        return EnumSet.allOf(Permission.class);
    }

    @Contract(" -> new")
    public static @NonNull Set<Permission> getNothing() {
        return EnumSet.noneOf(Permission.class);
    }

    @Contract(pure = true)
    public static @NonNull Set<Permission> of(@NonNull List<Integer> codes) throws IllegalArgumentException {
        Set<Permission> permissions = new HashSet<>();

        for (int code : codes) {
            var value = valueOfLabel(code);
            if (value == null) {
                throw new IllegalArgumentException(String.format("Permission with code '%d' doesn't exists", code));
            }

            permissions.add(value);
        }

        return permissions;
    }
}
