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

    @Override
    public void mapDomainToUserEntity(@NonNull User user, @NonNull UserEntity userEntity) {
        userEntity.setLogin(user.getLogin().getValue());
        userEntity.setEmail(user.getEmail().getAddress());
        userEntity.setFirstName(user.getFullName().getFirstName());
        userEntity.setMiddleName(user.getFullName().getMiddleName());
        userEntity.setLastName(user.getFullName().getLastName());
        userEntity.setPassword(user.getPassword().getValue());
        userEntity.setRegistrationDate(user.getRegistrationDate());
        userEntity.setPhoneNumber(user.getPhoneNumber().getNumber());
        userEntity.setBirthDate(user.getBirthDate().getDate());
        userEntity.setCity(user.getCity());
        userEntity.setAvatar(user.getAvatar());
    }
}
