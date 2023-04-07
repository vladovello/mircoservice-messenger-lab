package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.model.HashedPassword;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.BasicPassword;
import com.messenger.authandprofile.domain.model.valueobject.Email;
import com.messenger.authandprofile.domain.model.valueobject.Login;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserCommand implements Command<UserWithTokenDto> {
    private String login;
    private String email;
    @NonNull private String password;
    private String fullName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String city;

    public class RegisterUserCommandHandler implements Command.Handler<RegisterUserCommand, UserWithTokenDto> {
        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final TokenService tokenService;

        public RegisterUserCommandHandler(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
            this.userRepository = userRepository;
            this.userMapper = userMapper;
            this.tokenService = tokenService;
        }

        // TODO: 06.04.2023 Think about changing everything to OneOf<> (if it is convenient)
        @Override
        public UserWithTokenDto handle(RegisterUserCommand command) {
            if (userRepository.isExistsByLogin(login)) {
                UserNotFoundException.throwUserIsAlreadyExistsByLogin(login);
            } else if (userRepository.isExistsByEmail(email)) {
                UserNotFoundException.throwUserIsAlreadyExistsByEmail(email);
            }

            var user = User.registerUser(new Login(email), new Email(email), new HashedPassword(new BasicPassword(password)));
            userRepository.saveUser(user);

            var token = tokenService.generateAccessToken(user);

            return userMapper.mapToUserWithTokenDto(user, token);
        }
    }
}
