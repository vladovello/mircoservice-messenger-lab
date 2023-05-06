package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.ChatParticipantId;
import com.messenger.chat.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
public class ChatParticipant {
    @NonNull private ChatParticipantId chatParticipantId;
    @NonNull private User user;
    @NonNull private ChatId chatId;
    @NonNull private Set<ChatRole> chatRoles;

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

    public void addRole(ChatRole chatRole) {
        chatRoles.add(chatRole);
    }

    public boolean hasPermission(Permission permission) {
        return chatRoles.stream().anyMatch(chatRole -> chatRole.hasPermission(permission));
    }
}
