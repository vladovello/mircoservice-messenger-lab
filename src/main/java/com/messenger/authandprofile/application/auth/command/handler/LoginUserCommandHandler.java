package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.LoginUserCommand;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.exception.PasswordsDoesNotMatchException;
import com.messenger.authandprofile.application.auth.model.HashedPassword;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.valueobject.BasicPassword;
import com.messenger.authandprofile.domain.repository.UserRepository;

@SuppressWarnings("unused")
public class LoginUserCommandHandler implements Command.Handler<LoginUserCommand, UserWithTokenDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public LoginUserCommandHandler(
            UserRepository userRepository,
            UserMapper userMapper,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public UserWithTokenDto handle(LoginUserCommand command) {
        var user = userRepository.findUserByLogin(command.getLogin());

        if (user == null) {
            throw UserNotFoundException.createUserNotFoundByLoginException(command.getLogin());
        }

        var hashedPassword = new HashedPassword(new BasicPassword(command.getPassword()));
        if (!hashedPassword.equals(user.getPassword())) {
            throw new PasswordsDoesNotMatchException();
        }

        var token = tokenService.generateTokens(user);

        return userMapper.mapToUserWithTokenDto(user, token);
    }
}