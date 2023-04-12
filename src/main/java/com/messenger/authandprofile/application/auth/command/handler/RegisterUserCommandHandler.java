package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.RegisterUserCommand;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.model.HashedPassword;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.authandprofile.domain.repository.UserRepository;

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
        if (userRepository.isExistsByLogin(command.getLogin())) {
            throw UserAlreadyExistsException.createUserIsAlreadyExistsByLogin(command.getLogin());
        } else if (userRepository.isExistsByEmail(command.getEmail())) {
            throw UserAlreadyExistsException.createUserIsAlreadyExistsByEmail(command.getEmail());
        }

        var user = User.builder(new Login(command.getLogin()), new Email(command.getLogin()),
                new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName()),
                new HashedPassword(new BasicPassword(command.getPassword())))
                .withPhoneNumber(new PhoneNumber(command.getPhoneNumber()))
                .withCity(command.getCity())
                .registerUser();
        userRepository.addUser(user);

        var token = tokenService.generateTokens(user);

        return userMapper.mapToUserWithTokenDto(user, token);
    }
}