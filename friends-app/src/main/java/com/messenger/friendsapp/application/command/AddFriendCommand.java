package com.messenger.friendsapp.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddFriendCommand {
    @NonNull private UUID requesterId;
    @NonNull private UUID addresseeId;
    @NonNull private String firstName;
    private String middleName;
    @NonNull private String lastName;
}
