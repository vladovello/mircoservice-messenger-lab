package com.messenger.authandprofile.application.auth.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.model.HashedPassword;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserCommand implements Command<UserWithTokenDto> {
    @NonNull String login;
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String lastName;
    private String middleName;

    private String phoneNumber;
    private LocalDate birthDate;
    private String city;

    @SuppressWarnings("unused")
    public class RegisterUserCommandHandler implements Command.Handler<RegisterUserCommand, UserWithTokenDto> {
        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final TokenService tokenService;

        public RegisterUserCommandHandler(
                UserRepository userRepository, UserMapper userMapper, TokenService tokenService
        ) {
            this.userRepository = userRepository;
            this.userMapper = userMapper;
            this.tokenService = tokenService;
        }

        // TODO: 06.04.2023 Think about changing everything to OneOf<> (if it is convenient)
        @Override
        public UserWithTokenDto handle(RegisterUserCommand command) {
            if (userRepository.isExistsByLogin(login)) {
                throw UserAlreadyExistsException.createUserIsAlreadyExistsByLogin(login);
            } else if (userRepository.isExistsByEmail(email)) {
                throw UserAlreadyExistsException.createUserIsAlreadyExistsByEmail(email);
            }

            var user = new User.Builder(new Login(email), new Email(email),
                    new FullName(firstName, middleName, lastName),
                    new HashedPassword(new BasicPassword(password)))
                    .withPhoneNumber(new PhoneNumber(phoneNumber))
                    .withCity(city)
                    .registerUser();
            userRepository.saveUser(user);

            var token = tokenService.generateAccessToken(user);

            return userMapper.mapToUserWithTokenDto(user, token);
        }
    }
}
