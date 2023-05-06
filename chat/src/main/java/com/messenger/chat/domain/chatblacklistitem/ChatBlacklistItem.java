package com.messenger.chat.domain.chatblacklistitem;

import com.messenger.chat.domain.identity.ChatBlacklistId;
import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class ChatBlacklistItem {
    @NonNull private ChatBlacklistId id;
    @NonNull private UserId userId;
    @NonNull private ChatId chatId;

    protected ChatBlacklistItem(@NonNull ChatBlacklistId id, @NonNull UserId userId, @NonNull ChatId chatId) {
        this.id = id;
        this.userId = userId;
        this.chatId = chatId;
    }

    public static @NonNull ChatBlacklistItem createNew(@NonNull UserId userId, @NonNull ChatId chatId) {
        return new ChatBlacklistItem(new ChatBlacklistId(), userId, chatId);
    }

    public static @NonNull ChatBlacklistItem reconstruct(@NonNull ChatBlacklistId chatBlacklistId, @NonNull UserId userId, @NonNull ChatId chatId) {
        return new ChatBlacklistItem(chatBlacklistId, userId, chatId);
    }


}
