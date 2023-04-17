package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.LoginUserCommand;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.exception.PasswordsDoNotMatchException;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.auth.util.PasswordHelper;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.valueobject.BasicPassword;
import com.messenger.authandprofile.domain.repository.UserRepository;
import io.vavr.control.Either;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class LoginUserCommandHandler implements Command.Handler<LoginUserCommand, Either<UserNotFoundException, UserWithTokenDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final PasswordHelper passwordHelper;

    public LoginUserCommandHandler(
            UserRepository userRepository,
            UserMapper userMapper,
            TokenService tokenService,
            PasswordHelper passwordHelper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.passwordHelper = passwordHelper;
    }

    @Override
    public Either<UserNotFoundException, UserWithTokenDto> handle(@NonNull LoginUserCommand command) {
        var optionalUser = userRepository.findUserByLogin(command.getLogin());

        if (optionalUser.isEmpty()) {
            return Either.left(UserNotFoundException.byLogin(command.getLogin()));
        }

        var user = optionalUser.get();

        var doPasswordsMatch = passwordHelper.doPasswordsMatch(user, new BasicPassword(command.getPassword()));
        if (!doPasswordsMatch) {
            throw new PasswordsDoNotMatchException();
        }

        var token = tokenService.generateTokens(user);

        return Either.right(userMapper.mapToUserWithTokenDto(user, token));
    }
}