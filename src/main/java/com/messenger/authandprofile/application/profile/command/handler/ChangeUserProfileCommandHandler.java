package com.messenger.authandprofile.application.profile.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.command.ChangeUserProfileCommand;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.domain.model.valueobject.FullName;
import com.messenger.authandprofile.domain.model.valueobject.PhoneNumber;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class ChangeUserProfileCommandHandler implements Command.Handler<ChangeUserProfileCommand, UserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ChangeUserProfileCommandHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto handle(@NonNull ChangeUserProfileCommand command) {
        var optionalUser = userRepository.findUserById(command.getId());

        if (optionalUser.isEmpty()) {
            throw UserNotFoundException.createUserNotFoundByIdException(command.getId());
        }

        var user = optionalUser.get();
        
        user.updateUserProfile(
                new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName()),
                new BirthDate(command.getBirthDate()),
                new PhoneNumber(command.getPhoneNumber()),
                command.getCity(),
                command.getAvatar()
        );

        userRepository.updateUser(user.getId(), user);

        return userMapper.mapToUserDto(user);
    }
}
