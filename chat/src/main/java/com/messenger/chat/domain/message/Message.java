package com.messenger.chat.domain.message;

import com.messenger.chat.domain.message.converter.MessageTextConverter;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
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
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Message extends DomainEntity {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID id;
    @Column(nullable = false)
    @NonNull
    private UUID chatId;
    @Column(nullable = false)
    @NonNull
    private UUID userId;
    @Column(nullable = false)
    @NonNull
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @Convert(converter = MessageTextConverter.class)
    @NonNull
    private MessageText messageText;

    protected Message(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID userId,
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
            @NonNull UUID chatId,
            @NonNull UUID userId,
            @NonNull MessageText messageText
    ) {
        return new Message(generateId(), chatId, userId, messageText, LocalDateTime.now());
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NonNull Message reconstruct(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID userId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate
    ) {
        return new Message(id, chatId, userId, messageText, creationDate);
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
