package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chat.converter.ChatNameConverter;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.identity.AvatarId;
import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.converter.UuidConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Chat {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private ChatId id;
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private AvatarId avatarId;
    @Column(nullable = false)
    @NonNull
    private ChatType chatType;
    @Column(nullable = false)
    @Convert(converter = ChatNameConverter.class)
    @NonNull
    private ChatName chatName;

    protected Chat(
            @NonNull ChatId id,
            @NonNull AvatarId avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName
    ) {
        this.id = id;
        this.avatarId = avatarId;
        this.chatType = chatType;
        this.chatName = chatName;
    }

    protected Chat() {
        // For JPA
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull Chat createNew(
            @NonNull AvatarId avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName
    ) {
        return new Chat(new ChatId(), avatarId, chatType, chatName);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NonNull Chat reconstruct(
            @NonNull ChatId chatId,
            @NonNull AvatarId avatarId,
            @NonNull ChatType chatType,
            @NonNull ChatName chatName
    ) {
        return new Chat(chatId, avatarId, chatType, chatName);
    }

    public void changeChatName(ChatName chatName) {
        this.chatName = chatName;
    }

    public void changeChatAvatar(AvatarId avatarId) {
        this.avatarId = avatarId;
    }
}
