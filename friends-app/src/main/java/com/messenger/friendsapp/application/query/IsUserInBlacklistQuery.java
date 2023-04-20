package com.messenger.friendsapp.application.query;

import lombok.Data;

import java.util.UUID;

@Data
public class IsUserInBlacklistQuery {
    private UUID requesterId;
    private UUID addresseeId;
}
