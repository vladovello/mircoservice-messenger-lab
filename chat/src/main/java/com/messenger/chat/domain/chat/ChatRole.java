package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chatparticipant.converter.ChatRoleNameConverter;
import com.messenger.chat.domain.chatparticipant.converter.PermissionConverter;
import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/*
 * Создатель чата будет иметь роль с наивысшим приоритетом. Роль с таким приоритетом блокируется и её нельзя поменять
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class ChatRole {
    public static final short HIGHEST_PRIORITY = Short.MAX_VALUE;
    public static final short LOWEST_PRIORITY = 0;

    @Id
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private UUID roleId;
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private UUID chatId;
    @Column(nullable = false)
    @Convert(converter = ChatRoleNameConverter.class)
    @NonNull
    private ChatRoleName chatRoleName;
    @Column(nullable = false)
    @Convert(converter = PermissionConverter.class)
    @NonNull
    private Set<Permission> permissions;
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
        this.permissions = permissions;
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

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public boolean hasPermissions(List<Permission> permissions) {
        return this.permissions.containsAll(permissions);
    }

    void addPermission(Permission permission) {
        permissions.add(permission);
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
}
