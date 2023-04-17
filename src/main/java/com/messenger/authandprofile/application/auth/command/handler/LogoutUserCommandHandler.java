package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.messenger.authandprofile.application.auth.command.LogoutUserCommand;
import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.service.TokenService;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class LogoutUserCommandHandler implements Command.Handler<LogoutUserCommand, Voidy> {
    private final TokenService tokenService;

    public LogoutUserCommandHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Voidy handle(@NonNull LogoutUserCommand command) throws RefreshTokenNotFoundException {
        try {
            tokenService.invalidateRefreshToken(command.getRefreshToken());
        } catch (RefreshTokenNotFoundException e) {
            // TODO: 14.04.2023 add logging
            throw e;
        }

        return new Voidy();
    }
}
