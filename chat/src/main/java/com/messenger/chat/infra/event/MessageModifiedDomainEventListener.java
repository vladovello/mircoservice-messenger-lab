package com.messenger.chat.infra.event;

import com.messenger.chat.domain.message.event.MessageCreatedDomainEvent;
import com.messenger.sharedlib.infra.rabbitmq.message.chat.MessageCreatedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageModifiedDomainEventListener {
    private final AmqpTemplate template;

    public MessageModifiedDomainEventListener(@Qualifier("messageCreatedTemplate") AmqpTemplate template) {
        this.template = template;
    }

    @EventListener
    public void handleMessageCreatedDomainEvent(@NonNull MessageCreatedDomainEvent messageCreatedDomainEvent) {
        log.info(String.format("MessageCreatedDomainEvent handling started: %s", messageCreatedDomainEvent));

        template.convertAndSend(new MessageCreatedMessage(
                messageCreatedDomainEvent.getMessageId(),
                messageCreatedDomainEvent.getChatId(),
                messageCreatedDomainEvent.getUserId(),
                messageCreatedDomainEvent.getEventMessageText().getText()
        ));

        log.info("MessageCreatedDomainEvent handling succeed");
    }
}
