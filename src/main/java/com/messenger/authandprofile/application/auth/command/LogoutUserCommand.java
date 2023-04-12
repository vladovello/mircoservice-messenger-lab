package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutUserCommand implements Command<Voidy> {
    String accessToken;
    String refreshToken;
}
