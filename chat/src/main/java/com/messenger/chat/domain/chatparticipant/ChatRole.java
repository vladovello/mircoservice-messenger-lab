package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.chatparticipant.converter.ChatRoleNameConverter;
import com.messenger.chat.domain.chatparticipant.converter.PermissionConverter;
import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.RoleId;
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
import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class ChatRole {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private RoleId roleId;
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private ChatId chatId;
    @Column(nullable = false)
    @Convert(converter = ChatRoleNameConverter.class)
    @NonNull
    private ChatRoleName chatRoleName;
    @Column(nullable = false)
    @Convert(converter = PermissionConverter.class)
    @NonNull
    private Set<Permission> permissions;

    protected ChatRole(
            @NonNull RoleId roleId,
            @NonNull ChatId chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions
    ) {
        this.roleId = roleId;
        this.chatId = chatId;
        this.chatRoleName = chatRoleName;
        this.permissions = permissions;
    }

    protected ChatRole() {
        // For JPA
    }

    @Contract("_, _, _ -> new")
    public static @NonNull ChatRole createNew(
            @NonNull ChatId chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions
    ) {
        return new ChatRole(new RoleId(), chatId, chatRoleName, permissions);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NonNull ChatRole recreate(
            @NonNull RoleId roleId,
            @NonNull ChatId chatId,
            @NonNull ChatRoleName chatRoleName,
            @NonNull Set<Permission> permissions
    ) {
        return new ChatRole(roleId, chatId, chatRoleName, permissions);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
}
