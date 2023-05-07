package com.messenger.chat.domain.message;

import com.messenger.chat.domain.identity.ChatId;
import com.messenger.chat.domain.identity.MessageId;
import com.messenger.chat.domain.identity.UserId;
import com.messenger.chat.domain.identity.converter.UuidConverter;
import com.messenger.chat.domain.message.converter.MessageTextConverter;
import com.messenger.chat.domain.message.valueobject.MessageText;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Message {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private MessageId id;
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private ChatId chatId;
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private UserId userId;
    @Column(nullable = false)
    @NonNull
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @Convert(converter = MessageTextConverter.class)
    @NonNull
    private MessageText messageText;

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

    protected Message() {
        // For JPA
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
