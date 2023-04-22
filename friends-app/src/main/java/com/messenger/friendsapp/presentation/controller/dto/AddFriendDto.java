package com.messenger.friendsapp.presentation.controller.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class AddFriendDto {
    @NonNull private UUID addresseeId;
    @NonNull private String firstName;
    private String middleName;
    @NonNull private String lastName;
}
