package com.messenger.friendsapp.infra.event;

import com.messenger.friendsapp.domain.event.FriendshipCreatedEvent;
import com.messenger.friendsapp.domain.event.FriendshipDeletedEvent;
import com.messenger.sharedlib.rabbitmq.message.friends.FriendshipCreatedMessage;
import com.messenger.sharedlib.rabbitmq.message.friends.FriendshipDeletedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FriendshipModifiedDomainEventListener {
    private final AmqpTemplate template;

    public FriendshipModifiedDomainEventListener(@Qualifier("friendshipModifiedTemplate") AmqpTemplate template) {
        this.template = template;
    }

    @EventListener
    public void handleFriendshipCreatedEvent(@NonNull FriendshipCreatedEvent friendshipCreatedEvent) {
        template.convertAndSend(new FriendshipCreatedMessage(
                friendshipCreatedEvent.getFriendshipId(),
                friendshipCreatedEvent.getRequesterId(),
                friendshipCreatedEvent.getAddressee().getId(),
                friendshipCreatedEvent.getAddressee().getFullName().getFirstName(),
                friendshipCreatedEvent.getAddressee().getFullName().getMiddleName(),
                friendshipCreatedEvent.getAddressee().getFullName().getLastName()
        ));
    }

    @EventListener
    public void handleFriendshipDeletedEvent(@NonNull FriendshipDeletedEvent friendshipDeletedEvent) {
        template.convertAndSend(new FriendshipDeletedMessage(
                friendshipDeletedEvent.getFriendshipId(),
                friendshipDeletedEvent.getRequesterId(),
                friendshipDeletedEvent.getAddressee().getId(),
                friendshipDeletedEvent.getAddressee().getFullName().getFirstName(),
                friendshipDeletedEvent.getAddressee().getFullName().getMiddleName(),
                friendshipDeletedEvent.getAddressee().getFullName().getLastName()
        ));
    }
}
