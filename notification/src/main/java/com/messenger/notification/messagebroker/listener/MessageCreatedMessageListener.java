package com.messenger.notification.messagebroker.listener;

import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.sharedlib.rabbitmq.message.chat.MessageCreatedMessage;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "notificationQ")
public class MessageCreatedMessageListener {
    private final NotificationRepository notificationRepository;

    public MessageCreatedMessageListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitHandler
    public void handleBlacklistItemCreatedMessage(@NonNull MessageCreatedMessage messageCreatedMessage) {
        var notification = NotificationFactory.messageSentNotification(
                messageCreatedMessage.getUserId(),
                messageCreatedMessage.getChatId(),
                messageCreatedMessage.getMessageId(),
                messageCreatedMessage.getEventMessageText()
        );

        notificationRepository.save(notification);
    }
}
