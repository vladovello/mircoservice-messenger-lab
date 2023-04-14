package com.messenger.authandprofile.infra.domain.persistence.mapper;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import lombok.NonNull;

public interface UserEntityMapper {
    User mapToDomainModel(@NonNull UserEntity userEntity);
    UserEntity mapToUserEntity(@NonNull User user);
}
