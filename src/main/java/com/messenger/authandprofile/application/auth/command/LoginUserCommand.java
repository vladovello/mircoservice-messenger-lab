package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import lombok.NonNull;
import lombok.Value;

@Value
public class LoginUserCommand implements Command<UserWithTokenDto> {
    @NonNull String login;
    @NonNull String password;
}
