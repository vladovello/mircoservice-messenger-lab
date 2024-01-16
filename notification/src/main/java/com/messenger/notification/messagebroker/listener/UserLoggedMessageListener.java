package com.messenger.notification.messagebroker.listener;

import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.sharedlib.infra.rabbitmq.message.auth.UserLoggedMessage;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "notificationQ")
public class UserLoggedMessageListener {
    private final NotificationRepository notificationRepository;

    public UserLoggedMessageListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitHandler
    public void handleUserLoggedMessage(@NonNull UserLoggedMessage userLoggedMessage) {
        var notification = NotificationFactory.userLoggedNotification(userLoggedMessage.getUserId());
        notificationRepository.save(notification);
    }
}
