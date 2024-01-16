package com.messenger.chat.infra.rabbitmq;

import com.messenger.chat.application.command.ProfileChangedEventCommand;
import com.messenger.chat.application.command.UserCreatedEventCommand;
import com.messenger.chat.application.command.handler.ProfileChangedEventCommandHandler;
import com.messenger.chat.application.command.handler.UserCreatedEventCommandHandler;
import com.messenger.sharedlib.infra.rabbitmq.message.profile.ProfileChangedMessage;
import com.messenger.sharedlib.infra.rabbitmq.message.profile.UserCreatedMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// TODO: 22.05.2023 remove hardcoded value to config read
@Slf4j
@Component
@RabbitListener(queues = "chatQ")
public class IncomingUserModifiedMessageListener {
    private final UserCreatedEventCommandHandler userCreatedEventCommandHandler;
    private final ProfileChangedEventCommandHandler profileChangedEventCommandHandler;

    public IncomingUserModifiedMessageListener(UserCreatedEventCommandHandler userCreatedEventCommandHandler,
            ProfileChangedEventCommandHandler profileChangedEventCommandHandler
    ) {
        this.userCreatedEventCommandHandler = userCreatedEventCommandHandler;
        this.profileChangedEventCommandHandler = profileChangedEventCommandHandler;
    }

    @RabbitHandler
    public void handleUserCreatedMessage(@NonNull UserCreatedMessage userCreatedMessage) {
        log.info(String.format("UserCreatedMessage handling started: %s", userCreatedMessage));

        var result = userCreatedEventCommandHandler.handle(new UserCreatedEventCommand(
                userCreatedMessage.getUserId(),
                userCreatedMessage.getFirstName(),
                userCreatedMessage.getLastName(),
                userCreatedMessage.getMiddleName(),
                userCreatedMessage.getBirthDate(),
                userCreatedMessage.getAvatarId()
        ));

        result.foldConsume(
                unit -> log.info("UserCreatedMessage handling succeed"),
                e -> log.info(String.format(
                        "UserCreatedMessage handling is failed. Exception message: %s",
                        e.getMessage()
                ))
        );
    }

    @RabbitHandler
    public void handleProfileChangedMessage(@NonNull ProfileChangedMessage profileChangedMessage) {
        log.info(String.format("ProfileChangedMessage handling started: %s", profileChangedMessage));

        var result = profileChangedEventCommandHandler.handle(new ProfileChangedEventCommand(
                profileChangedMessage.getUserId(),
                profileChangedMessage.getFirstName(),
                profileChangedMessage.getLastName(),
                profileChangedMessage.getMiddleName(),
                profileChangedMessage.getBirthDate(),
                profileChangedMessage.getAvatarId()
        ));

        result.foldConsume(
                unit -> log.info("ProfileChangedMessage handling succeed"),
                e -> log.info(String.format(
                        "ProfileChangedMessage handling is failed. Exception message: %s",
                        e.getMessage()
                ))
        );
    }
}
