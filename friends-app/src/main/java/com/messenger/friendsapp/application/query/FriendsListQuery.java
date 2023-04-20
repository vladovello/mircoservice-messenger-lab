package com.messenger.friendsapp.application.query;

import lombok.Data;

import java.util.UUID;

@Data
public class FriendsListQuery {
    private int pageSize;
    private int pageNumber;
    private UUID userId;
    private String fullName;
}
