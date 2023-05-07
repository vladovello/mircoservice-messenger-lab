package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.ChatParticipantId;
import com.messenger.chat.domain.user.User;
import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class ChatParticipant {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private ChatParticipantId chatParticipantId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;
    @Column(nullable = false)
    @Convert(converter = UuidIdentity.class)
    @NonNull
    private ChatId chatId;
    @ManyToMany
    @JoinTable
    @NonNull
    private Set<ChatRole> chatRoles;

    public ChatParticipant(
            @NonNull ChatParticipantId chatParticipantId,
            @NonNull User user,
            @NonNull ChatId chatId,
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

    public void addRole(ChatRole chatRole) {
        chatRoles.add(chatRole);
    }

    public boolean hasPermission(Permission permission) {
        return chatRoles.stream().anyMatch(chatRole -> chatRole.hasPermission(permission));
    }
}
