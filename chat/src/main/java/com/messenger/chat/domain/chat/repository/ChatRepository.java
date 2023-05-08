package com.messenger.chat.domain.chat.repository;

import com.messenger.chat.domain.chat.Chat;
import lombok.NonNull;

import java.util.UUID;

public interface ChatRepository {
    Chat getChat(UUID chatId);
    @NonNull Chat save(@NonNull Chat chat);
}
