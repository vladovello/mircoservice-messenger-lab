package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.ChatRole;
import com.messenger.chat.domain.chat.Permission;
import com.messenger.chat.domain.user.ChatUser;
import com.messenger.sharedlib.domain.ddd.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(indexes = {@Index(name = "idx_chat_user_id_chat_id_unique", columnList = "chatId, chatUserId", unique = true)})
public class ChatParticipant extends DomainEntity {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID chatParticipantId;

    @Column(name = "chatUserId", nullable = false) private UUID userId;

    @ManyToOne
    @JoinColumn(name = "chatUserId", insertable = false, updatable = false)
    private ChatUser chatUser;

    @Column(name = "chatId", nullable = false)
    @NonNull
    private UUID chatId;

    @ManyToOne
    @JoinColumn(name = "chatId", insertable = false, updatable = false)
    private Chat chat;

    @ManyToMany
    @JoinTable
    @NonNull
    private Set<ChatRole> chatRoles;

    protected ChatParticipant(
            @NonNull UUID chatParticipantId,
            @NonNull UUID userId,
            @NonNull UUID chatId,
            @NonNull Set<ChatRole> chatRoles
    ) {
        this.chatParticipantId = chatParticipantId;
        this.userId = userId;
        this.chatId = chatId;
        this.chatRoles = chatRoles;
    }

    protected ChatParticipant() {
        // For JPA
    }

    public static @NonNull ChatParticipant createNew(
            @NonNull UUID userId,
            @NonNull UUID chatId,
            @NonNull Set<ChatRole> chatRoles
    ) {
        return new ChatParticipant(generateId(), userId, chatId, chatRoles);
    }

    public static @NonNull ChatParticipant createNew(
            @NonNull UUID userId,
            @NonNull UUID chatId,
            @NonNull ChatRole... chatRoles
    ) {
        return new ChatParticipant(generateId(), userId, chatId, Arrays.stream(chatRoles).collect(Collectors.toSet()));
    }

    public void addRole(ChatRole chatRole) {
        chatRoles.add(chatRole);
    }

    public boolean hasPermission(Permission permission) {
        return chatRoles.stream().anyMatch(chatRole -> chatRole.hasPermission(permission));
    }

    public Optional<ChatRole> getHighestPriorityRole() {
        return chatRoles.stream().max((ChatRole::compareTo));
    }

    public Optional<ChatRole> getHighestPriorityRoleWithPermission(Permission permission) {
        return chatRoles.stream()
                .filter(chatRole -> chatRole.hasPermission(permission)).max((ChatRole::compareTo));
    }

    public boolean isOwner() {
        return chatRoles.stream().anyMatch(chatRole -> chatRole.getPriority() == ChatRole.HIGHEST_PRIORITY);
    }

    public boolean isNotInSameChat(@NonNull ChatParticipant otherChatParticipant) {
        return this.getChatId() != otherChatParticipant.getChatId();
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
