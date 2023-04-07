package com.messenger.authandprofile.application.shared.mapper;

import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.domain.model.entity.User;

public interface UserMapper {
    User mapToUserDto(UserDto userDto);
    UserDto mapToUser(User user);
    UserWithTokenDto mapToUserWithTokenDto(User user, String token);
    User mapToUser(UserWithTokenDto userWithTokenDto);
}
