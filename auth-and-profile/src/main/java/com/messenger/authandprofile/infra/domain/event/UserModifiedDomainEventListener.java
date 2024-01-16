package com.messenger.authandprofile.infra.domain.event;

import com.messenger.authandprofile.domain.event.ProfileChangedEvent;
import com.messenger.authandprofile.domain.event.UserCreatedEvent;
import com.messenger.sharedlib.infra.rabbitmq.message.profile.ProfileChangedMessage;
import com.messenger.sharedlib.infra.rabbitmq.message.profile.UserCreatedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserModifiedDomainEventListener {
    private final AmqpTemplate template;

    public UserModifiedDomainEventListener(@Qualifier("userModifiedTemplate") AmqpTemplate template) {
        this.template = template;
    }

    @EventListener
    public void handleUserCreatedEvent(@NonNull UserCreatedEvent event) {
        log.info("UserCreatedEvent handling is in progress");
        log.info(event.toString());

        var ucm = new UserCreatedMessage(
                event.getUserId(),
                event.getFullName().getFirstName(),
                event.getFullName().getLastName(),
                event.getFullName().getMiddleName(),
                event.getBirthDate().getDate(),
                event.getAvatarId()
        );

        template.convertAndSend(ucm);
    }

    @EventListener
    public void handleProfileChangedEvent(@NonNull ProfileChangedEvent event) {
        log.info("ProfileChangedEvent handling is in progress");
        log.info(event.toString());

        template.convertAndSend(new ProfileChangedMessage(
                event.getUserId(),
                event.getFullName().getFirstName(),
                event.getFullName().getLastName(),
                event.getFullName().getMiddleName(),
                event.getBirthDate().getDate(),
                event.getAvatarId()
        ));
    }
}
