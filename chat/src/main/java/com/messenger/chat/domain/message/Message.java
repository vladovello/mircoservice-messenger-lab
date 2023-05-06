package com.messenger.chat.domain.message;

import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.MessageId;
import com.messenger.chat.domain.identity.UserId;
import com.messenger.chat.domain.message.valueobject.MessageText;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Message {
    @NonNull private MessageId id;
    @NonNull private ChatId chatId;
    @NonNull private UserId userId;
    @NonNull private LocalDateTime creationDate;
    @NonNull private MessageText messageText;

    protected Message(
            @NonNull MessageId id,
            @NonNull ChatId chatId,
            @NonNull UserId userId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate
    ) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.creationDate = creationDate;
        this.messageText = messageText;
    }

    @Contract("_, _, _ -> new")
    public static @NonNull Message createNew(
            @NonNull ChatId chatId,
            @NonNull UserId userId,
            @NonNull MessageText messageText
    ) {
        return new Message(new MessageId(), chatId, userId, messageText, LocalDateTime.now());
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NonNull Message reconstruct(
            @NonNull MessageId id,
            @NonNull ChatId chatId,
            @NonNull UserId userId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate
    ) {
        return new Message(id, chatId, userId, messageText, creationDate);
    }
}
