package com.messenger.authandprofile.application.auth.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class UserWithTokenDto {
    @NonNull UUID id;
    @NonNull String login;
    @NonNull String email;
    @NonNull String registrationDate;
    @NonNull String accessToken;
    @NonNull String refreshToken;
    String fullName;
    String phoneNumber;
    LocalDate birthDate;
    String city;
    UUID avatar;
}
