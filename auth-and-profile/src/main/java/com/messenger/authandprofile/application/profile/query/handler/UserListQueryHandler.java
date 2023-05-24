package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.exception.ConstraintViolationException;
import com.messenger.authandprofile.application.profile.exception.IntervalException;
import com.messenger.authandprofile.application.profile.model.UserListDto;
import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.application.profile.query.UserListQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.repository.UserRepository;
import io.vavr.control.Either;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
public class UserListQueryHandler implements Command.Handler<UserListQuery, Either<Exception, UserListDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserListQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Either<Exception, UserListDto> handle(@NonNull UserListQuery query) {
        if (query.getPageSize() < 0) {
            return Either.left(new ConstraintViolationException("Page size must not be less than one"));
        }

        try {
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

            var profileDtoList = users.stream().map(userMapper::mapToProfileDto).collect(Collectors.toList());

            return Either.right(new UserListDto(
                    profileDtoList.size(),
                    query.getPageNumber(),
                    profileDtoList
            ));
        } catch (IntervalException e) {
            return Either.left(e);
        }
    }
}
