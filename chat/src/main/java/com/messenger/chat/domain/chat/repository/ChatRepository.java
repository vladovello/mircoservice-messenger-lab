package com.messenger.chat.domain.chat.repository;

import com.messenger.chat.domain.chat.Chat;
import lombok.NonNull;

public interface ChatRepository {
    Chat update(Chat chat);
    @NonNull Chat save(@NonNull Chat chat);
}
