package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.messenger.authandprofile.application.auth.command.LogoutUserCommand;
import com.messenger.authandprofile.application.auth.service.TokenService;

@SuppressWarnings("unused")
public class LogoutUserCommandHandler implements Command.Handler<LogoutUserCommand, Voidy> {
    private final TokenService tokenService;

    public LogoutUserCommandHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Voidy handle(LogoutUserCommand command) {
        tokenService.invalidateAccessToken(command.getAccessToken());
        tokenService.invalidateRefreshToken(command.getRefreshToken());

        return new Voidy();
    }
}
