package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RegisterUserCommand implements Command<UserWithTokenDto> {
    @NonNull String login;
    @NonNull String email;
    @NonNull String password;
    @NonNull String firstName;
    @NonNull String lastName;

    String middleName;
    String phoneNumber;
    LocalDate birthDate;
    String city;
}
