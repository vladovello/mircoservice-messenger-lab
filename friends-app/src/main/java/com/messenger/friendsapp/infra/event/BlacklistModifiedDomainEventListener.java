package com.messenger.friendsapp.infra.event;

import com.messenger.friendsapp.domain.event.BlacklistItemCreatedEvent;
import com.messenger.friendsapp.domain.event.BlacklistItemDeletedEvent;
import com.messenger.sharedlib.infra.rabbitmq.message.blacklist.BlacklistItemCreatedMessage;
import com.messenger.sharedlib.infra.rabbitmq.message.blacklist.BlacklistItemDeletedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlacklistModifiedDomainEventListener {
    private final AmqpTemplate template;

    public BlacklistModifiedDomainEventListener(@Qualifier("blacklistModifiedTemplate") AmqpTemplate template) {
        this.template = template;
    }

    @EventListener
    public void handleBlacklistItemCreatedEvent(@NonNull BlacklistItemCreatedEvent blacklistItemCreatedEvent) {
        log.info(String.format("BlacklistItemCreatedEvent handling started: %s", blacklistItemCreatedEvent));

        template.convertAndSend(new BlacklistItemCreatedMessage(
                blacklistItemCreatedEvent.getBlacklistItemId(),
                blacklistItemCreatedEvent.getRequesterId(),
                blacklistItemCreatedEvent.getAddressee().getId(),
                blacklistItemCreatedEvent.getAddressee().getFullName().getFirstName(),
                blacklistItemCreatedEvent.getAddressee().getFullName().getMiddleName(),
                blacklistItemCreatedEvent.getAddressee().getFullName().getLastName()
        ));

        log.info("BlacklistItemCreatedEvent handling succeed");
    }

    @EventListener
    public void handleBlacklistItemDeletedEvent(@NonNull BlacklistItemDeletedEvent blacklistItemDeletedEvent) {
        log.info(String.format("BlacklistItemDeletedEvent handling started: %s", blacklistItemDeletedEvent));

        template.convertAndSend(new BlacklistItemDeletedMessage(
                blacklistItemDeletedEvent.getBlacklistItemId(),
                blacklistItemDeletedEvent.getRequesterId(),
                blacklistItemDeletedEvent.getAddressee().getId(),
                blacklistItemDeletedEvent.getAddressee().getFullName().getFirstName(),
                blacklistItemDeletedEvent.getAddressee().getFullName().getMiddleName(),
                blacklistItemDeletedEvent.getAddressee().getFullName().getLastName()
        ));

        log.info("BlacklistItemDeletedEvent handling succeed");
    }
}
