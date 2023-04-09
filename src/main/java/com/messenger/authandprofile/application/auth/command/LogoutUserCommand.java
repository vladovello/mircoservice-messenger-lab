package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import lombok.Value;

@Value
public class LogoutUserCommand implements Command<Voidy> {
    String accessToken;
    String refreshToken;
}
