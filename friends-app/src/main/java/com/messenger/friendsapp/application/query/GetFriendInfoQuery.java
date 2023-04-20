package com.messenger.friendsapp.application.query;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetFriendInfoQuery {
    private UUID userId;
    private UUID friendId;
}
