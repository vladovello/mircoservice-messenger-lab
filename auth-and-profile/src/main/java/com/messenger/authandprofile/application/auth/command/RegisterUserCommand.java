package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException
 */
@Data
@NoArgsConstructor
public class RegisterUserCommand implements Command<Either<Exception, UserWithTokenDto>> {
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private String middleName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String city;
}
