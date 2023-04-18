package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserCommand implements Command<Either<Exception, UserWithTokenDto>> {
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
