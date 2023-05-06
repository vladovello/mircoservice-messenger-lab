package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.RoleId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
public class ChatRole {
    private final RoleId roleId;
    private final ChatId chatId;
    private final ChatRoleName chatRoleName;
    private final Set<Permission> permissions;

    protected ChatRole(RoleId roleId, ChatId chatId, ChatRoleName chatRoleName, Set<Permission> permissions) {
        this.roleId = roleId;
        this.chatId = chatId;
        this.chatRoleName = chatRoleName;
        this.permissions = permissions;
    }

    @Contract("_, _, _ -> new")
    public static @NonNull ChatRole createNew(ChatId chatId, ChatRoleName chatRoleName, Set<Permission> permissions) {
        return new ChatRole(new RoleId(), chatId, chatRoleName, permissions);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NonNull ChatRole recreate(RoleId roleId, ChatId chatId, ChatRoleName chatRoleName, Set<Permission> permissions) {
        return new ChatRole(roleId, chatId, chatRoleName, permissions);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
}
