package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommand implements Command<UserWithTokenDto> {
    private String login;
    private String email;
    private String password;


}
