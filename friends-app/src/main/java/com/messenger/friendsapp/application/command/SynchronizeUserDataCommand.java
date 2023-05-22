package com.messenger.friendsapp.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SynchronizeUserDataCommand {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String middleName;
}
