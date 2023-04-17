package com.messenger.authandprofile.infra.util.mapper;

import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.model.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto mapToUserDto(@NonNull User user) {
        String phoneNumber = null;
        LocalDate birthDate = null;

        if (user.getPhoneNumber() != null) {
            phoneNumber = user.getPhoneNumber().getNumber();
        }
        if (user.getBirthDate() != null) {
            birthDate = user.getBirthDate().getDate();
        }

        return new UserDto(
                user.getId(),
                user.getLogin().getValue(),
                user.getEmail().getAddress(),
                user.getRegistrationDate(),
                user.getFullName().getFirstName(),
                user.getFullName().getMiddleName(),
                user.getFullName().getLastName(),
                phoneNumber,
                birthDate,
                user.getCity(),
                user.getAvatar()
        );
    }

    @Override
    public UserWithTokenDto mapToUserWithTokenDto(@NonNull User user, @NonNull TokenPair token) {
        String phoneNumber = null;
        LocalDate birthDate = null;

        if (user.getPhoneNumber() != null) {
            phoneNumber = user.getPhoneNumber().getNumber();
        }
        if (user.getBirthDate() != null) {
            birthDate = user.getBirthDate().getDate();
        }

        return new UserWithTokenDto(
                user.getId(),
                user.getLogin().getValue(),
                user.getEmail().getAddress(),
                user.getRegistrationDate(),
                token.getAccessToken(),
                token.getRefreshToken(),
                user.getFullName().getFirstName(),
                user.getFullName().getMiddleName(),
                user.getFullName().getLastName(),
                phoneNumber,
                birthDate,
                user.getCity(),
                user.getAvatar()
        );
    }
}
