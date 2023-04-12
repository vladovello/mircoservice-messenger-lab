package com.messenger.authandprofile.application.profile.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class UserDto {
    @NonNull UUID id;
    @NonNull String login;
    @NonNull String email;
    @NonNull String registrationDate;
    String firstName;
    String middleName;
    String lastName;
    String phoneNumber;
    LocalDate birthDate;
    String city;
    UUID avatar;
}
