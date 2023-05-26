package com.messenger.friendsapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FriendsListDto {
    private int pageSize;
    private int pageNumber;
    private List<FriendDto> friends;
}
