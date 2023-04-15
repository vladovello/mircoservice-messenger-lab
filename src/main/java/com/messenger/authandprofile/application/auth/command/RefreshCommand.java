package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshCommand implements Command<Optional<TokenPairDto>> {
    UUID userId;
    String refreshToken;
}
