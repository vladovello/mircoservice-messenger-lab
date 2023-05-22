package com.messenger.notification.messagebroker.listener;

import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.sharedlib.rabbitmq.message.auth.UserLoggedMessage;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

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
