package com.messenger.friendsapp.infra.persistence.mapper;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.entity.Friendship;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.infra.persistence.entity.FriendshipEntity;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.stream.Collectors;

public final class FriendshipEntityMapper {
    private FriendshipEntityMapper() {
    }

    public static @NonNull Friendship mapToDomainModel(@NonNull FriendshipEntity friendshipEntity) {
        return Friendship.reconstructFriendship(
                friendshipEntity.getId(),
                friendshipEntity.getRequesterId(),
                Addressee.createAddressee(
                        friendshipEntity.getAddresseeId(),
                        new FullName(
                                friendshipEntity.getAddresseeFirstName(),
                                friendshipEntity.getAddresseeMiddleName(),
                                friendshipEntity.getAddresseeLastName()
                        )
                ),
                friendshipEntity.getFriendshipStatus(),
                friendshipEntity.getAdditionDate()
        );
    }

    public static @NonNull List<Friendship> mapToDomainModelList(@NonNull List<FriendshipEntity> friendshipEntityList) {
        return friendshipEntityList.stream().map(FriendshipEntityMapper::mapToDomainModel).collect(Collectors.toList());
    }

    @Contract("_ -> new")
    public static @NonNull FriendshipEntity mapToEntity(@NonNull Friendship friendship) {
        var fe = new FriendshipEntity(
                friendship.getId(),
                friendship.getRequesterId(),
                friendship.getAddressee().getId(),
                friendship.getAddressee().getFullName().getFirstName(),
                friendship.getAddressee().getFullName().getMiddleName(),
                friendship.getAddressee().getFullName().getLastName(),
                friendship.getAddressee().getFullName().getName(),
                friendship.getFriendshipStatus(),
                friendship.getAdditionDate()
        );
        fe.setDomainEvents(friendship.getDomainEvents());

        return fe;
    }
}
