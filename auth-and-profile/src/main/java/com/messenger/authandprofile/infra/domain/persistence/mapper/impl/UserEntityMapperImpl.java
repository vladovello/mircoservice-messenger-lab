package com.messenger.authandprofile.infra.domain.persistence.mapper.impl;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import com.messenger.authandprofile.infra.domain.persistence.mapper.UserEntityMapper;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
                .reconstructUser(userEntity.getId(), userEntity.getRegistrationDate());
    }

    @Override
    public UserEntity mapToUserEntity(@NonNull User user) {
        String phoneNumber = null;
        LocalDate birthDate = null;

        if (user.getPhoneNumber() != null) {
            phoneNumber = user.getPhoneNumber().getNumber();
        }
        if (user.getBirthDate() != null) {
            birthDate = user.getBirthDate().getDate();
        }

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
                .withPhoneNumber(phoneNumber)
                .withBirthDate(birthDate)
                .withCity(user.getCity())
                .withAvatar(user.getAvatar())
                .withDomainEvents(user.getDomainEvents())
                .build();
    }

    @Override
    public void mapDomainToUserEntity(@NonNull User user, @NonNull UserEntity userEntity) {
        String phoneNumber = null;
        LocalDate birthDate = null;

        if (user.getPhoneNumber() != null) {
            phoneNumber = user.getPhoneNumber().getNumber();
        }
        if (user.getBirthDate() != null) {
            birthDate = user.getBirthDate().getDate();
        }

        userEntity.setLogin(user.getLogin().getValue());
        userEntity.setEmail(user.getEmail().getAddress());
        userEntity.setFirstName(user.getFullName().getFirstName());
        userEntity.setMiddleName(user.getFullName().getMiddleName());
        userEntity.setLastName(user.getFullName().getLastName());
        userEntity.setPassword(user.getPassword().getValue());
        userEntity.setRegistrationDate(user.getRegistrationDate());
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setBirthDate(birthDate);
        userEntity.setCity(user.getCity());
        userEntity.setAvatar(user.getAvatar());
        userEntity.setDomainEvents(user.getDomainEvents());
    }
}
