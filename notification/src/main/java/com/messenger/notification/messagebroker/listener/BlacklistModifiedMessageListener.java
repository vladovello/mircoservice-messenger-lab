package com.messenger.notification.messagebroker.listener;

import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.helper.FullNameHelper;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.sharedlib.rabbitmq.message.blacklist.BlacklistItemCreatedMessage;
import com.messenger.sharedlib.rabbitmq.message.blacklist.BlacklistItemDeletedMessage;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "notificationQ")
public class BlacklistModifiedMessageListener {
    private final NotificationRepository notificationRepository;

    public BlacklistModifiedMessageListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitHandler
    public void handleBlacklistItemCreatedMessage(@NonNull BlacklistItemCreatedMessage blacklistItemCreatedMessage) {
        var notification = NotificationFactory.blacklistItemCreatedNotification(
                blacklistItemCreatedMessage.getAddresseeId(),
                FullNameHelper.concatFullName(blacklistItemCreatedMessage.getFirstName(),
                        blacklistItemCreatedMessage.getLastName(),
                        blacklistItemCreatedMessage.getMiddleName()
                )
        );

        notificationRepository.save(notification);
    }

    @RabbitHandler
    public void handleBlacklistItemDeletedMessage(@NonNull BlacklistItemDeletedMessage blacklistItemDeletedMessage) {
        var notification = NotificationFactory.blacklistItemDeletedNotification(
                blacklistItemDeletedMessage.getAddresseeId(),
                FullNameHelper.concatFullName(blacklistItemDeletedMessage.getFirstName(),
                        blacklistItemDeletedMessage.getLastName(),
                        blacklistItemDeletedMessage.getMiddleName()
                )
        );
        notificationRepository.save(notification);
    }
}
