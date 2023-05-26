package com.messenger.friendsapp.infra.rabbitmq;

import com.messenger.friendsapp.application.command.UpdateUserDataCommand;
import com.messenger.friendsapp.application.command.handler.UpdateUserDataCommandHandler;
import com.messenger.sharedlib.rabbitmq.message.profile.ProfileChangedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// TODO: 22.05.2023 remove hardcoded value to config read
@Slf4j
@Component
@RabbitListener(queues = "friendsQ")
public class IncomingUserModifiedMessageListener {
    private final UpdateUserDataCommandHandler updateUserDataCommandHandler;

    public IncomingUserModifiedMessageListener(UpdateUserDataCommandHandler updateUserDataCommandHandler) {
        this.updateUserDataCommandHandler = updateUserDataCommandHandler;
    }

    @RabbitHandler
    public void handleProfileChangedMessage(@NonNull ProfileChangedMessage profileChangedMessage) {
        log.info(String.format("ProfileChangedMessage proceeding started: %s", profileChangedMessage));

        updateUserDataCommandHandler.handle(new UpdateUserDataCommand(
                profileChangedMessage.getUserId(),
                profileChangedMessage.getFirstName(),
                profileChangedMessage.getLastName(),
                profileChangedMessage.getMiddleName()
        ));

        log.info(String.format("ProfileChangedMessage proceeding completed: %s", profileChangedMessage));
    }
}
