package com.messenger.friendsapp.application.dto;

import com.messenger.friendsapp.domain.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FriendDto {
    private LocalDate additionDate;
    private UUID friendId;
    private String fullName;

    @Contract("_ -> new")
    public static @NonNull FriendDto fromFriendship(@NonNull Friendship friendship) {
        return new FriendDto(
                friendship.getAdditionDate(),
                friendship.getAddressee().getId(),
                friendship.getAddressee().getFullName().getName()
        );
    }
}
