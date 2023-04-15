package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.query.GetOtherProfileInfoQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.service.DomainUserService;
import lombok.NonNull;

import java.util.Optional;

@SuppressWarnings("unused")
public class GetOtherProfileInfoQueryHandler implements Command.Handler<GetOtherProfileInfoQuery, Optional<UserDto>> {
    private final DomainUserService domainUserService;
    private final UserMapper userMapper;

    public GetOtherProfileInfoQueryHandler(DomainUserService domainUserService, UserMapper userMapper) {
        this.domainUserService = domainUserService;
        this.userMapper = userMapper;
    }

    // TODO: 14.04.2023 change to `OtherUserProfileDto`
    @Override
    public Optional<UserDto> handle(@NonNull GetOtherProfileInfoQuery query) {
        var optionalUser = domainUserService.getOtherUserProfile(query.getSelfId(), query.getOtherId());
        return optionalUser.map(userMapper::mapToUserDto);
    }
}
