package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.application.profile.query.UserListQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserListQueryHandler implements Command.Handler<UserListQuery, List<UserDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserListQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> handle(@NonNull UserListQuery query) {
        var filterParameters = UserFilterParams.builder()
                .withLogin(query.getLogin())
                .withFullName(query.getFullName())
                .withPhoneNumber(query.getPhoneNumber())
                .withCity(query.getCity())
                .withBirthDate(query.getBirthDate())
                .withRegistrationDate(query.getRegistrationDate())
                .build();

        var users = userRepository.findAllUsersPaginatedWithParams(
                query.getPageNumber(),
                query.getPageSize(),
                filterParameters
        );

        return users.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }
}
