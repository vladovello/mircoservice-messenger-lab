package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chat.converter.ChatNameConverter;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.message.Message;
import com.messenger.sharedlib.domain.ddd.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(indexes = {@Index(columnList = "chatName")})
public class Chat extends DomainEntity {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID id;

    @Column private UUID avatarId;

    @Column(nullable = false)
    @NonNull
    private ChatType chatType;

    @Column
    @Convert(converter = ChatNameConverter.class)
    private ChatName chatName;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @NonNull
    private Set<ChatRole> roles;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NonNull
    private Set<ChatParticipant> chatParticipants;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NonNull
    private Set<Message> messages;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime creationDate;

    public Chat(
            @NonNull UUID id,
            UUID avatarId,
            @NonNull ChatType chatType,
            ChatName chatName,
            @NonNull Set<ChatRole> roles,
            @NonNull LocalDateTime creationDate
    ) {
        this.id = id;
        this.avatarId = avatarId;
        this.chatType = chatType;
        this.chatName = chatName;
        this.roles = roles;
        this.creationDate = creationDate;
    }

    protected Chat() {
        // For JPA
    }

    @Contract(value = " -> new", pure = true)
    public static @NonNull Chat createDialogue() {
        var chatId = generateId();

        var roles = new HashSet<ChatRole>();
        roles.add(RoleFactory.createEveryone(chatId));

        return new Chat(
                chatId,
                null,
                ChatType.DIALOGUE,
                null,
                roles,
                LocalDateTime.now()
        );
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NonNull Chat createMulti(
            @NonNull UUID avatarId,
            @NonNull ChatName chatName
    ) {
        var chatId = generateId();

        var roles = new HashSet<ChatRole>();
        roles.add(RoleFactory.createOwner(chatId));
        roles.add(RoleFactory.createEveryone(chatId));

        return new Chat(
                chatId,
                avatarId,
                ChatType.MULTI,
                chatName,
                roles,
                LocalDateTime.now()
        );
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NonNull Chat reconstruct(
            @NonNull UUID chatId,
            @NonNull UUID avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName,
            @NonNull Set<ChatRole> roles,
            @NonNull LocalDateTime creationDate
    ) {
        return new Chat(chatId, avatarId, chatType, chatName, roles, creationDate);
    }

    public void changeChat(ChatName chatName, UUID avatarId) {
        if (chatName != null) this.chatName = chatName;
        if (avatarId != null) this.avatarId = avatarId;
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
