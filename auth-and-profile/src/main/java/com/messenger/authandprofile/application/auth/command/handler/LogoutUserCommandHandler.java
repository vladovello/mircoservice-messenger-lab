package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.messenger.authandprofile.application.auth.command.LogoutUserCommand;
import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.service.TokenService;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
@Slf4j
public class LogoutUserCommandHandler implements Command.Handler<LogoutUserCommand, Either<Exception, Voidy>> {
    private final TokenService tokenService;

    public LogoutUserCommandHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Either<Exception, Voidy> handle(@NonNull LogoutUserCommand command) {
        try {
            tokenService.invalidateRefreshToken(command.getRefreshToken());
        } catch (RefreshTokenNotFoundException e) {
            log.warn(String.format("Refresh token '%s' was not found.", command.getRefreshToken()));
            return Either.left(e);
        }

        log.info("User logged out.");
        return Either.right(new Voidy());
    }
}
