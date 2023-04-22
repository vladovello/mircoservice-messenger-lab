package com.messenger.friendsapp.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IsUserInBlacklistQuery {
    private UUID requesterId;
    private UUID addresseeId;
}
