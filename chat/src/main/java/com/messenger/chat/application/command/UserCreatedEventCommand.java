package com.messenger.chat.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCreatedEventCommand {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private UUID avatarId;
}
