package com.messenger.chat.domain.message;

import com.messenger.chat.domain.businessrule.StringIsNotBlankRule;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.message.converter.MessageTextConverter;
import com.messenger.chat.domain.message.event.MessageCreatedDomainEvent;
import com.messenger.chat.domain.message.valueobject.EventMessageText;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.chat.domain.user.ChatUser;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
import com.messenger.sharedlib.ddd.domain.DomainEvent;
import com.messenger.sharedlib.util.Result;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

// INFO: Чтобы убедиться, что событие будет доставлен только один раз (за исключением ситуаций, когда сервер упал) можно добавить
//  различные состояния у событий: PENDING, LOCKED, PROCESSED. Все обработчики обязаны будут реализовать интерфейс, с
//  четырьмя методами: beforeProcessing, afterProcessing и process. В beforeProcessing и afterProcessing будет установка
//  состояний LOCKED и PROCESSED соответственно, а метод process нужно будет реализовать.
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(indexes = {@Index(name = "idx_message_text", columnList = "messageText")})
public class Message extends DomainEntity {
    @Id
    @Column(name = "messageId", nullable = false)
    @NonNull
    private UUID id;

    @Column(name = "chatId", nullable = false)
    @NonNull
    private UUID chatId;

    @ManyToOne
    @JoinColumn(name = "chatId", insertable = false, updatable = false)
    private Chat chat;

    @Column(name = "userId", nullable = false)
    @NonNull
    private UUID senderUserId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private ChatUser chatUser;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime creationDate;

    @Column(nullable = false, length = 5000)
    @Convert(converter = MessageTextConverter.class)
    @NonNull
    private MessageText messageText;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Attachment> attachments;

    @Transient
    private final List<DomainEvent> domainEvents;

    protected Message(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate,
            Set<Attachment> attachments
    ) {
        this.id = id;
        this.chatId = chatId;
        this.senderUserId = senderUserId;
        this.creationDate = creationDate;
        this.messageText = messageText;
        this.attachments = attachments;

        domainEvents = new ArrayList<>();
    }

    protected Message() {
        // For JPA
        domainEvents = new ArrayList<>();
    }

    @Contract("_, _, _ -> new")
    public static @NonNull Result<Message> createNew(
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText
    ) {
        var messageId = generateId();

        try {
            checkRule(new StringIsNotBlankRule(messageText.getText(), "Message"));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        var message = new Message(messageId, chatId, senderUserId, messageText, LocalDateTime.now(), null);

        message.domainEvents.add(new MessageCreatedDomainEvent(
                messageId,
                chatId,
                senderUserId,
                EventMessageText.create(messageText)
        ));

        return Result.success(message);
    }

    @Contract("_, _, _, _ -> new")
    public static @NonNull Result<Message> createNew(
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText,
            @NonNull Set<Attachment> attachments
    ) {
        var messageId = generateId();

        var message = new Message(messageId, chatId, senderUserId, messageText, LocalDateTime.now(), attachments);

        message.domainEvents.add(new MessageCreatedDomainEvent(
                messageId,
                chatId,
                senderUserId,
                EventMessageText.create(messageText)
        ));

        return Result.success(message);
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NonNull Message reconstruct(
            @NonNull UUID id,
            @NonNull UUID chatId,
            @NonNull UUID senderUserId,
            @NonNull MessageText messageText,
            @NonNull LocalDateTime creationDate,
            Set<Attachment> attachments
    ) {
        return new Message(id, chatId, senderUserId, messageText, creationDate, attachments);
    }

    @DomainEvents
    public List<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void assignAttachments(List<Attachment> attachments) {
        this.attachments = new HashSet<>(attachments);
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }
}
