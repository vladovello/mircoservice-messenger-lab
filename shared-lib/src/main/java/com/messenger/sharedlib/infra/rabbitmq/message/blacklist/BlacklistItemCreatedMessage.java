package com.messenger.sharedlib.infra.rabbitmq.message.blacklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistItemCreatedMessage {
    private UUID blacklistItemId;
    private UUID requesterId;
    private UUID addresseeId;
    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public String toString() {
        return "BlacklistItemCreatedEvent{" +
                "blacklistItemId=" + blacklistItemId +
                ", requesterId=" + requesterId +
                ", addresseeId=" + addresseeId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
