package com.messenger.friendsapp.presentation.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddToBlacklistDto {
    private UUID addresseeId;
    private String firstName;
    private String middleName;
    private String lastName;
}
