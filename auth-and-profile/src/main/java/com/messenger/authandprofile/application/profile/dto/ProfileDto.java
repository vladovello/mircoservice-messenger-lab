package com.messenger.authandprofile.application.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    @NonNull private UUID id;
    @NonNull private String login;
    @NonNull private String email;
    @NonNull private LocalDate registrationDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String city;
    private UUID avatar;
}
