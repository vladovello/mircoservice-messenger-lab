package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.exception.PasswordsDoNotMatchException;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see UserNotFoundException
 * @see PasswordsDoNotMatchException
 */
@Data
@NoArgsConstructor
public class LoginUserCommand implements Command<Either<Exception, UserWithTokenDto>> {
    private String login;
    private String password;
}
