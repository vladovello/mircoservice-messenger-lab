package com.messenger.authandprofile.application.profile.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.command.EditUserProfileCommand;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.domain.model.valueobject.FullName;
import com.messenger.authandprofile.domain.model.valueobject.PhoneNumber;
import com.messenger.authandprofile.domain.repository.UserRepository;
import io.vavr.control.Either;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class EditUserProfileCommandHandler implements Command.Handler<EditUserProfileCommand, Either<Exception, UserDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public EditUserProfileCommandHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Either<Exception, UserDto> handle(@NonNull EditUserProfileCommand command) {
        var optionalUser = userRepository.findUserById(command.getId());

        if (optionalUser.isEmpty()) {
            return Either.left(UserNotFoundException.byId(command.getId()));
        }

        var user = optionalUser.get();

        // TODO: 19.04.2023 add handling domain layer exceptions
        user.updateUserProfile(
                new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName()),
                new BirthDate(command.getBirthDate()),
                new PhoneNumber(command.getPhoneNumber()),
                command.getCity(),
                command.getAvatar()
        );

        userRepository.updateUser(user.getId(), user);

        return Either.right(userMapper.mapToUserDto(user));
    }
}
