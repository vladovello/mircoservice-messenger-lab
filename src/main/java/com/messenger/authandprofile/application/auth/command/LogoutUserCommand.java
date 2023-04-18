package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException
 */
@Data
@NoArgsConstructor
public class LogoutUserCommand implements Command<Either<Exception, Voidy>> {
    private String accessToken;
    private String refreshToken;
}
