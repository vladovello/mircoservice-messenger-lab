package com.messenger.authandprofile.application.shared.mapper;

import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.domain.model.entity.User;
import lombok.NonNull;

public interface UserMapper {
    UserDto mapToUserDto(@NonNull User user);
    UserWithTokenDto mapToUserWithTokenDto(@NonNull User user, @NonNull TokenPair token);
}
