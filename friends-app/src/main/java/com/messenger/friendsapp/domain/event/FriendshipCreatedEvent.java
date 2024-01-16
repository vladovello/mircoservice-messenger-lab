package com.messenger.friendsapp.domain.event;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.sharedlib.domain.ddd.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipCreatedEvent extends BaseDomainEvent {
    private UUID friendshipId;
    private UUID requesterId;
    private Addressee addressee;
    private FriendshipStatus friendshipStatus;

    @Override
    public String toString() {
        return "FriendshipCreatedEvent{" +
                "friendshipId=" + friendshipId +
                ", requesterId=" + requesterId +
                ", addressee=" + addressee +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }
}
