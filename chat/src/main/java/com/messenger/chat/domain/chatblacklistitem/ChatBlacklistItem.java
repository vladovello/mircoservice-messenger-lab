package com.messenger.chat.domain.chatblacklistitem;

import com.messenger.chat.domain.identity.ChatBlacklistId;
import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.converter.UuidConverter;
import com.messenger.chat.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class ChatBlacklistItem {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private ChatBlacklistId id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private ChatId chatId;

    protected ChatBlacklistItem(@NonNull ChatBlacklistId id, @NonNull User user, @NonNull ChatId chatId) {
        this.id = id;
        this.user = user;
        this.chatId = chatId;
    }

    protected ChatBlacklistItem() {
        // For JPA
    }

    public static @NonNull ChatBlacklistItem createNew(@NonNull User user, @NonNull ChatId chatId) {
        return new ChatBlacklistItem(new ChatBlacklistId(), user, chatId);
    }

    public static @NonNull ChatBlacklistItem reconstruct(
            @NonNull ChatBlacklistId chatBlacklistId,
            @NonNull User user,
            @NonNull ChatId chatId
    ) {
        return new ChatBlacklistItem(chatBlacklistId, user, chatId);
    }
}
