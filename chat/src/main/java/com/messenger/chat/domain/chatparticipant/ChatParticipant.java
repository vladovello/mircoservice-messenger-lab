package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.chat.ChatRole;
import com.messenger.chat.domain.chat.Permission;
import com.messenger.chat.domain.user.User;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
import com.messenger.sharedlib.ddd.domain.UuidIdentity;
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
public class ChatParticipant extends DomainEntity {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private UUID chatParticipantId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private UUID chatId;
    @ManyToMany
    @JoinTable
    @NonNull
    private Set<ChatRole> chatRoles;

    protected ChatParticipant(
            @NonNull UUID chatParticipantId,
            @NonNull User user,
            @NonNull UUID chatId,
            @NonNull Set<ChatRole> chatRoles
    ) {
        this.chatParticipantId = chatParticipantId;
        this.user = user;
        this.chatId = chatId;
        this.chatRoles = chatRoles;
    }

    protected ChatParticipant() {
        // For JPA
    }

    public static @NonNull ChatParticipant createNew(
            @NonNull User user,
            @NonNull UUID chatId,
            @NonNull Set<ChatRole> chatRoles
    ) {
        return new ChatParticipant(generateId(), user, chatId, chatRoles);
    }

    public static @NonNull ChatParticipant createNew(
            @NonNull User user,
            @NonNull UUID chatId,
            @NonNull ChatRole... chatRoles
    ) {
        return new ChatParticipant(generateId(), user, chatId, Arrays.stream(chatRoles).collect(Collectors.toSet()));
    }

    public void addRole(ChatRole chatRole) {
        chatRoles.add(chatRole);
    }

    public boolean hasPermission(Permission permission) {
        return chatRoles.stream().anyMatch(chatRole -> chatRole.hasPermission(permission));
    }

    public Optional<ChatRole> getHighestPriorityRole() {
        return chatRoles.stream().max(((o1, o2) -> o1.compare(o1, o2)));
    }

    public Optional<ChatRole> getHighestPriorityRoleWithPermission(Permission permission) {
        return chatRoles.stream()
                .filter(chatRole -> chatRole.hasPermission(permission)).max(((o1, o2) -> o1.compare(o1, o2)));
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
