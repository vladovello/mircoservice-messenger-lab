package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import com.messenger.authandprofile.application.profile.query.GetSelfProfileInfoQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("unused")
@Component
public class GetSelfProfileInfoQueryHandler implements Command.Handler<GetSelfProfileInfoQuery, Optional<ProfileDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetSelfProfileInfoQueryHandler(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<ProfileDto> handle(@NonNull GetSelfProfileInfoQuery query) {
        var optionalUser = userRepository.findUserById(query.getPayloadPrincipal().getId());
        return optionalUser.map(userMapper::mapToProfileDto);
    }
}
