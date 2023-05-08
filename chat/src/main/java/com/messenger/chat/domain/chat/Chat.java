package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chat.converter.ChatNameConverter;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Chat extends DomainEntity {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID id;
    @Column(nullable = false)
    @NonNull
    private UUID avatarId;
    @Column(nullable = false)
    @NonNull
    private ChatType chatType;
    @Column(nullable = false)
    @Convert(converter = ChatNameConverter.class)
    @NonNull
    private ChatName chatName;
    @OneToMany
    @JoinColumn(name = "chat_role_id")
    @NonNull
    private Set<ChatRole> roles;

    public Chat(
            @NonNull UUID id,
            @NonNull UUID avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName,
            @NonNull Set<ChatRole> roles
    ) {
        this.id = id;
        this.avatarId = avatarId;
        this.chatType = chatType;
        this.chatName = chatName;
        this.roles = roles;
    }

    protected Chat() {
        // For JPA
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull Chat createDialogue(
            @NonNull UUID avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName
    ) {
        var chatId = generateId();
        var roles = new HashSet<ChatRole>();
        roles.add(RoleFactory.createEveryone(chatId));

        return new Chat(
                chatId,
                avatarId,
                chatType,
                chatName,
                roles
        );
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull Chat createMulti(
            @NonNull UUID avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName
    ) {
        var chatId = generateId();
        var roles = new HashSet<ChatRole>();
        roles.add(RoleFactory.createOwner(chatId));
        roles.add(RoleFactory.createEveryone(chatId));

        return new Chat(
                chatId,
                avatarId,
                chatType,
                chatName,
                roles
        );
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NonNull Chat reconstruct(
            @NonNull UUID chatId,
            @NonNull UUID avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName,
            @NonNull Set<ChatRole> roles
    ) {
        return new Chat(chatId, avatarId, chatType, chatName, roles);
    }

    public void changeChatName(ChatName chatName) {
        this.chatName = chatName;
    }

    public void changeChatAvatar(UUID avatarId) {
        this.avatarId = avatarId;
    }

    public ChatRole getEveryoneRole() {
        return roles.stream().filter(ChatRole::isEveryone).findAny().get();
    }

    public Optional<ChatRole> getOwnerRole() {
        return roles.stream().filter(ChatRole::isOwner).findAny();
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
