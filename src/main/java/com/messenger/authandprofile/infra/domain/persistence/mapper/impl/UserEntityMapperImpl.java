package com.messenger.authandprofile.infra.domain.persistence.mapper.impl;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import com.messenger.authandprofile.infra.domain.persistence.mapper.UserEntityMapper;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {
    @Override
    public User mapToDomainModel(@NonNull UserEntity userEntity) {
        return User
                .builder(
                        new Login(userEntity.getLogin()),
                        new Email(userEntity.getEmail()),
                        new FullName(userEntity.getFirstName(), userEntity.getMiddleName(), userEntity.getLastName()),
                        new BasicPassword(userEntity.getPassword())
                )
                .withPhoneNumber(new PhoneNumber(userEntity.getPhoneNumber()))
                .withCity(userEntity.getCity())
                .withAvatar(userEntity.getAvatar())
                .reconstructUser(userEntity.getId());
    }

    @Override
    public UserEntity mapToUserEntity(@NonNull User user) {
        return UserEntity
                .builder()
                .withId(user.getId())
                .withLogin(user.getLogin().getValue())
                .withEmail(user.getEmail().getAddress())
                .withFirstName(user.getFullName().getFirstName())
                .withMiddleName(user.getFullName().getMiddleName())
                .withLastName(user.getFullName().getLastName())
                .withPassword(user.getPassword().getValue())
                .withRegistrationDate(user.getRegistrationDate())
                .withPhoneNumber(user.getPhoneNumber().getNumber())
                .withBirthDate(user.getBirthDate().getDate())
                .withCity(user.getCity())
                .withAvatar(user.getAvatar())
                .build();
    }
}
