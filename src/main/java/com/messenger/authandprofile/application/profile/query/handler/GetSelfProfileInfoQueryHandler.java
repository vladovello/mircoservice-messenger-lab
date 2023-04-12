package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.query.GetSelfProfileInfoQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;

@SuppressWarnings("unused")
public class GetSelfProfileInfoQueryHandler implements Command.Handler<GetSelfProfileInfoQuery, UserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetSelfProfileInfoQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto handle(@NonNull GetSelfProfileInfoQuery query) {
        var user = userRepository.findUserById(query.getId());

        if (user == null) {
            throw UserNotFoundException.createUserNotFoundByIdException(query.getId());
        }

        return userMapper.mapToUserDto(user);
    }
}
