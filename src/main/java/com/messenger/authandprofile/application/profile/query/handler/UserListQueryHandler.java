package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.model.UserListDto;
import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.application.profile.query.UserListQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserListQueryHandler implements Command.Handler<UserListQuery, UserListDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserListQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserListDto handle(@NonNull UserListQuery query) {
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

        var userList = users.stream().map(userMapper::mapToProfileDto).collect(Collectors.toList());

        return new UserListDto(
                userList.size(),
                query.getPageNumber(),
                userList
        );
    }
}
