package com.messenger.friendsapp.domain.aggregate;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Friendship {
    @NonNull private UUID id;
    @NonNull private UUID requesterId;
    @NonNull private Addressee addressee;
    @NonNull private FriendshipStatus friendshipStatus;

    private Friendship(
            @NonNull UUID requesterId,
            @NonNull Addressee addressee,
            @NonNull FriendshipStatus friendshipStatus
    ) {
        setRequesterId(requesterId);
        setAddressee(addressee);
        setFriendshipStatus(friendshipStatus);
    }

    public static @NonNull Friendship createNewFriendship(UUID requesterId, Addressee addressee) {
        var friendship = new Friendship(requesterId, addressee, FriendshipStatus.PENDING);
        friendship.generateId();
        return friendship;
    }

    public static @NonNull Friendship createNewFriendship(
            UUID requesterId,
            Addressee addressee,
            FriendshipStatus friendshipStatus
    ) {
        var friendship = new Friendship(requesterId, addressee, friendshipStatus);
        friendship.generateId();
        return friendship;
    }

    public static @NonNull Friendship reconstructFriendship(
            UUID friendshipId,
            UUID requesterId,
            Addressee addressee,
            FriendshipStatus friendshipStatus
    ) {
        var friendship = new Friendship(requesterId, addressee, friendshipStatus);
        friendship.setId(friendshipId);
        return friendship;
    }

    public void setMutualStatus() {
        setFriendshipStatus(FriendshipStatus.MUTUAL);
    }

    public void setPendingStatus() {
        setFriendshipStatus(FriendshipStatus.PENDING);
    }

    private void generateId() {
        id = UUID.randomUUID();
    }
}
