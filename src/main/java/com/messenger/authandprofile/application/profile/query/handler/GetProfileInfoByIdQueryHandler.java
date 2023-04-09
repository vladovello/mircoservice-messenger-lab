package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.query.GetProfileInfoByIdQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;

@SuppressWarnings("unused")
public class GetProfileInfoByIdQueryHandler implements Command.Handler<GetProfileInfoByIdQuery, UserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetProfileInfoByIdQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto handle(@NonNull GetProfileInfoByIdQuery query) {
        var user = userRepository.findUserById(query.getId());
        return userMapper.mapToUser(user);
    }
}
