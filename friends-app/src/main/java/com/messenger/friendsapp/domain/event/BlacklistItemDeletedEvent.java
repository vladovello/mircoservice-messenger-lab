package com.messenger.friendsapp.domain.event;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.sharedlib.ddd.domain.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BlacklistItemDeletedEvent extends BaseDomainEvent {
    private UUID blacklistItemId;
    private UUID requesterId;
    private Addressee addressee;

    @Override
    public String toString() {
        return "BlacklistItemDeletedEvent{" +
                "blacklistItemId=" + blacklistItemId +
                ", requesterId=" + requesterId +
                ", addressee=" + addressee +
                '}';
    }
}
