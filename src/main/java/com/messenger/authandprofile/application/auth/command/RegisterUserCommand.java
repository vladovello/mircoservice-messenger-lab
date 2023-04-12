package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserCommand implements Command<UserWithTokenDto> {
    String login;
    String email;
    String password;
    String firstName;
    String lastName;

    String middleName;
    String phoneNumber;
    LocalDate birthDate;
    String city;
}
