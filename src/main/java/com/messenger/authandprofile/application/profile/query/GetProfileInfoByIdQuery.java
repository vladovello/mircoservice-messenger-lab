package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.domain.repository.UserRepository;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class GetProfileInfoByIdQuery implements Command<UserDto> {
    private UUID id;

    public class GetProfileInfoByIdQueryHandler implements Command.Handler<GetProfileInfoByIdQuery, UserDto> {
        private final UserRepository userRepository;
        private final UserMapper userMapper;

        public GetProfileInfoByIdQueryHandler(UserRepository userRepository, UserMapper userMapper) {
            this.userRepository = userRepository;
            this.userMapper = userMapper;
        }

        @Override
        public UserDto handle(@NonNull GetProfileInfoByIdQuery getProfileInfoByIdQuery) {
            var user = userRepository.findUserById(getProfileInfoByIdQuery.id);
            return userMapper.mapToUser(user);
        }
    }
}
