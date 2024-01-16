package com.messenger.sharedlib.infra.rabbitmq.message.friends;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipCreatedMessage {
    private UUID friendshipId;
    private UUID requesterId;
    private UUID addresseeId;
    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public String toString() {
        return "FriendshipCreatedMessage{" +
                "friendshipId=" + friendshipId +
                ", requesterId=" + requesterId +
                ", addresseeId=" + addresseeId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
