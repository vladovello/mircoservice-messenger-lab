package com.messenger.friendsapp.application.dto;

import com.messenger.friendsapp.domain.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Contract;

import java.util.List;

@Data
@AllArgsConstructor
public class FriendsListDto {
    private int pageSize;
    private int pageNumber;
    private List<FriendDto> friends;
}
