package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.exception.RefreshTokenReuseException;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see RefreshTokenReuseException
 * @see UserNotFoundException
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshCommand implements Command<Either<Exception, TokenPairDto>> {
    UUID userId;
    String refreshToken;
}
