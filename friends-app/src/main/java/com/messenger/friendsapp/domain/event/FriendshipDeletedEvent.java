package com.messenger.friendsapp.domain.event;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.sharedlib.domain.ddd.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FriendshipDeletedEvent extends BaseDomainEvent {
    private UUID friendshipId;
    private UUID requesterId;
    private Addressee addressee;

    @Override
    public String toString() {
        return "FriendshipDeletedEvent{" +
                "friendshipId=" + friendshipId +
                ", requesterId=" + requesterId +
                ", addressee=" + addressee +
                '}';
    }
}
