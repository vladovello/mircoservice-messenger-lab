package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import lombok.Value;

import java.util.UUID;

@Value
public class RefreshCommand implements Command<TokenPairDto> {
    UUID userId;
    String refreshToken;
}
