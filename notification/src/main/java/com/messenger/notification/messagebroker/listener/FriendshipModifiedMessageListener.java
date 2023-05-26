package com.messenger.notification.messagebroker.listener;

import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.helper.FullNameHelper;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.sharedlib.rabbitmq.message.friends.FriendshipCreatedMessage;
import com.messenger.sharedlib.rabbitmq.message.friends.FriendshipDeletedMessage;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "notificationQ")
public class FriendshipModifiedMessageListener {
    private final NotificationRepository notificationRepository;

    public FriendshipModifiedMessageListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitHandler
    public void handleFriendshipCreatedMessage(@NonNull FriendshipCreatedMessage friendshipCreatedMessage) {
        var notification = NotificationFactory.friendshipCreatedNotification(
                friendshipCreatedMessage.getAddresseeId(),
                FullNameHelper.concatFullName(friendshipCreatedMessage.getFirstName(),
                        friendshipCreatedMessage.getLastName(),
                        friendshipCreatedMessage.getMiddleName()
                )
        );
        notificationRepository.save(notification);
    }

    @RabbitHandler
    public void handleFriendshipDeletedMessage(@NonNull FriendshipDeletedMessage friendshipDeletedMessage) {
        var notification = NotificationFactory.friendshipDeletedNotification(
                friendshipDeletedMessage.getAddresseeId(),
                FullNameHelper.concatFullName(friendshipDeletedMessage.getFirstName(),
                        friendshipDeletedMessage.getLastName(),
                        friendshipDeletedMessage.getMiddleName()
                )
        );
        notificationRepository.save(notification);
    }
}
