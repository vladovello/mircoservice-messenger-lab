package com.messenger.chat.domain.chat;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ChatRoleList {
    @Getter private final Set<ChatRole> chatRoles;

    public ChatRoleList(ChatRole... chatRoles) {
        this.chatRoles = new HashSet<>(Arrays.asList(chatRoles));
    }

    public Optional<ChatRole> getEveryone() {
        return chatRoles.stream().filter(ChatRole::isEveryone).findAny();
    }

    public Optional<ChatRole> getOwner() {
        return chatRoles.stream().filter(ChatRole::isOwner).findAny();
    }
}
