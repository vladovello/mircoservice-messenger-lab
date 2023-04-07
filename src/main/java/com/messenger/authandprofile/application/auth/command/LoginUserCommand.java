package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.exception.PasswordsDoesNotMatchException;
import com.messenger.authandprofile.application.auth.model.HashedPassword;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.valueobject.BasicPassword;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommand implements Command<UserWithTokenDto> {
    @NonNull private String login;
    @NonNull private String password;

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
        public UserWithTokenDto handle(LoginUserCommand loginUserCommand) {
            var user = userRepository.findUserByLogin(login);

            if (user == null) {
                throw UserNotFoundException.createUserNotFoundByLoginException(login);
            }

            var hashedPassword = new HashedPassword(new BasicPassword(password));
            if (!hashedPassword.equals(user.getPassword())) {
                throw new PasswordsDoesNotMatchException();
            }

            var token = tokenService.generateAccessToken(user);

            return userMapper.mapToUserWithTokenDto(user, token);
        }
    }
}
