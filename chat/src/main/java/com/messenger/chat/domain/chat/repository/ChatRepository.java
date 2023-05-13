package com.messenger.chat.domain.chat.repository;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {
    Optional<Chat> getChat(UUID chatId);
    Optional<Chat> getDialogue(UUID userId1, UUID userId2);
    ChatType getChatType(UUID chatId);
    boolean isChatExists(UUID chatId);
    boolean isDialogueExists(UUID userId1, UUID userId2);
    @NonNull Chat save(@NonNull Chat chat);
}
