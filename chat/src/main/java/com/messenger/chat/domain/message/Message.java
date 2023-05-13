package com.messenger.chat.domain.message;

import com.messenger.chat.domain.message.converter.MessageTextConverter;
import com.messenger.chat.domain.message.event.MessageCreatedDomainEvent;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
import com.messenger.sharedlib.ddd.domain.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

// INFO: Чтобы убедиться, что событие будет доставлен только один раз (за исключением ситуаций, когда сервер упал) можно добавить
//  различные состояния у событий: PENDING, LOCKED, PROCESSED. Все обработчики обязаны будут реализовать интерфейс, с
//  четырьмя методами: beforeProcessing, afterProcessing и process. В beforeProcessing и afterProcessing будет установка
//  состояний LOCKED и PROCESSED соответственно, а метод process нужно будет реализовать.
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

    @Transient
    private final Collection<DomainEvent> domainEvents;

    protected Message(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate
    ) {
        this.id = id;
        this.chatId = chatId;
        this.userId = senderUserId;
        this.creationDate = creationDate;
        this.messageText = messageText;
        domainEvents = new ArrayList<>();
    }

    protected Message() {
        // For JPA
        domainEvents = new ArrayList<>();
    }

    @Contract("_, _, _ -> new")
    public static @NonNull Message createNew(
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText
    ) {
        var messageId = generateId();
        var message = new Message(messageId, chatId, senderUserId, messageText, LocalDateTime.now());
        message.domainEvents.add(new MessageCreatedDomainEvent(messageId, chatId, senderUserId));
        return message;
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NonNull Message reconstruct(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate
    ) {
        return new Message(id, chatId, senderUserId, messageText, creationDate);
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
