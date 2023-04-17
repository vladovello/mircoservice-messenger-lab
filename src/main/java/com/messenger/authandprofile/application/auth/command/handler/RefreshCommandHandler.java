package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.RefreshCommand;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.mapper.TokenPairMapper;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.repository.UserRepository;
import io.vavr.control.Either;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("unused")
@Component
public class RefreshCommandHandler implements Command.Handler<RefreshCommand, Either<UserNotFoundException, Optional<TokenPairDto>>> {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenPairMapper tokenPairMapper;

    public RefreshCommandHandler(
            TokenService tokenService, UserRepository userRepository,
            TokenPairMapper tokenPairMapper
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.tokenPairMapper = tokenPairMapper;
    }

    /*
     * 1. Получить информацию о том, использовался ли данный рефреш токен
     * 2. Если токен уже использовался, мы инвалидируем всё семейство рефреш токенов (refresh token family) и ничего не возвращаем
     * 3. Если токен не использовался:
     *   3.1. Состояние только использованного токена меняем на "использован"
     *   3.2. Генерируем новую пару токенов и возвращаем их пользователю
     */
    @Override
    public Either<UserNotFoundException, Optional<TokenPairDto>> handle(@NonNull RefreshCommand command) {
        if (tokenService.isRefreshTokenInvalidated(command.getRefreshToken())) {
            tokenService.invalidateRefreshTokenFamily(command.getUserId());
            return Either.right(Optional.empty());
        }

        var optionalUser = userRepository.findUserById(command.getUserId());
        if (optionalUser.isEmpty()) {
            return Either.left(UserNotFoundException.byId(command.getUserId()));
        }

        var tokenPair = tokenService.generateTokens(optionalUser.get());
        tokenService.invalidateRefreshToken(command.getRefreshToken());

        return Either.right(Optional.ofNullable(tokenPairMapper.mapToTokenPairDto(tokenPair)));
    }
}
