package com.messenger.friendsapp.presentation.controller.dto;

import lombok.Data;

@Data
public class FriendsListRequestDto {
    private int pageSize;
    private int pageNumber;
    private String fullName;
}
