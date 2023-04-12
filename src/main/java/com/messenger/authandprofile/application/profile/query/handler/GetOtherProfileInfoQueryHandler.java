package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.query.GetOtherProfileInfoQuery;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.service.DomainUserService;
import lombok.NonNull;

@SuppressWarnings("unused")
public class GetOtherProfileInfoQueryHandler implements Command.Handler<GetOtherProfileInfoQuery, UserDto> {
    private final DomainUserService domainUserService;
    private final UserMapper userMapper;

    public GetOtherProfileInfoQueryHandler(DomainUserService domainUserService, UserMapper userMapper) {
        this.domainUserService = domainUserService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto handle(@NonNull GetOtherProfileInfoQuery query) {
        var user = domainUserService.getOtherUserProfile(query.getSelfId(), query.getOtherId());
        return userMapper.mapToUserDto(user);
    }
}
