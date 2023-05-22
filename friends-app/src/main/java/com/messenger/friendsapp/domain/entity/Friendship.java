package com.messenger.friendsapp.domain.entity;

import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.sharedlib.ddd.domain.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(value = AccessLevel.PROTECTED)
public class Friendship {
    @NonNull private UUID id;
    @NonNull private UUID requesterId;
    @NonNull private Addressee addressee;
    @NonNull private FriendshipStatus friendshipStatus;
    private LocalDate additionDate;

    @NonNull private List<DomainEvent> domainEvents = new ArrayList<>();

    private Friendship(
            @NonNull UUID requesterId,
            @NonNull Addressee addressee,
            @NonNull FriendshipStatus friendshipStatus
    ) {
        setRequesterId(requesterId);
        setAddressee(addressee);
        setFriendshipStatus(friendshipStatus);
    }

    public static @NonNull Friendship createNewFriendship(
            UUID requesterId,
            Addressee addressee,
            FriendshipStatus friendshipStatus
    ) {
        var friendship = new Friendship(requesterId, addressee, friendshipStatus);
        friendship.generateId();
        friendship.setAdditionDate();
        return friendship;
    }

    public static @NonNull Friendship createNewFriendship(UUID requesterId, Addressee addressee) {

        return createNewFriendship(requesterId, addressee, FriendshipStatus.PENDING);
    }

    public static @NonNull Friendship reconstructFriendship(
            UUID friendshipId,
            UUID requesterId,
            Addressee addressee,
            FriendshipStatus friendshipStatus,
            LocalDate additionDate
    ) {
        var friendship = new Friendship(requesterId, addressee, friendshipStatus);
        friendship.setId(friendshipId);
        friendship.setAdditionDate(additionDate);
        return friendship;
    }

    public void clearEvents() {
        this.domainEvents.clear();
    }

    public void setMutualStatus() {
        setFriendshipStatus(FriendshipStatus.MUTUAL);
    }

    public void setPendingStatus() {
        setFriendshipStatus(FriendshipStatus.PENDING);
    }

    public void setAdditionDate() {
        this.additionDate = LocalDate.now();
    }

    private void generateId() {
        id = UUID.randomUUID();
    }
}
