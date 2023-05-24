package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chatparticipant.converter.ChatRoleNameConverter;
import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/*
 * Создатель чата будет иметь роль с наивысшим приоритетом. Роль с таким приоритетом блокируется и её нельзя поменять
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class ChatRole implements Comparable<ChatRole> {
    public static final short HIGHEST_PRIORITY = Short.MAX_VALUE;
    public static final short LOWEST_PRIORITY = 0;

    @Id
    @Column(nullable = false)
    @NonNull
    private UUID roleId;

    @Column(name = "chatId", nullable = false)
    @NonNull
    private UUID chatId;

    @ManyToOne
    @JoinColumn(name = "chatId", insertable = false, updatable = false)
    private Chat chat;

    @Column(nullable = false)
    @Convert(converter = ChatRoleNameConverter.class)
    @NonNull
    private ChatRoleName chatRoleName;

    @Column(nullable = false)
    private int permissions;

    @Column(nullable = false) private int priority;

    protected ChatRole(
            @NonNull UUID roleId,
            @NonNull UUID chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions,
            int priority
    ) {
        this.roleId = roleId;
        this.chatId = chatId;
        this.chatRoleName = chatRoleName;
        this.permissions = permissions.stream().map(Permission::getCode).reduce(0, Integer::sum);
        this.priority = priority;
    }

    protected ChatRole() {
        // For JPA
    }

    @Contract("_, _, _, _ -> new")
    public static @NonNull ChatRole createNew(
            @NonNull UUID chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions,
            int priority
    ) {
        return new ChatRole(generateId(), chatId, chatRoleName, permissions, priority);
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NonNull ChatRole recreate(
            @NonNull UUID roleId,
            @NonNull UUID chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions,
            int priority
    ) {
        return new ChatRole(roleId, chatId, chatRoleName, permissions, priority);
    }

    public boolean hasPermission(@NonNull Permission permission) {
        return (permissions & permission.getCode()) == permission.getCode();
    }

    public boolean hasPermissions(@NonNull Set<Permission> permissions) {
        return permissions.stream().map(Permission::getCode).reduce(0, Integer::sum).equals(this.permissions);
    }

    void addPermission(@NonNull Permission permission) {
        permissions |= permission.getCode();
    }

    boolean isEveryone() {
        return getPriority() == LOWEST_PRIORITY;
    }

    public boolean isOwner() {
        return getPriority() == HIGHEST_PRIORITY;
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }

    @Override
    public int compareTo(@NonNull ChatRole o) {
        if (this.getPriority() == o.getPriority()) return 0;
        return this.getPriority() > o.getPriority() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRole)) return false;

        ChatRole chatRole = (ChatRole) o;

        return getRoleId().equals(chatRole.getRoleId());
    }

    @Override
    public int hashCode() {
        return getRoleId().hashCode();
    }
}
