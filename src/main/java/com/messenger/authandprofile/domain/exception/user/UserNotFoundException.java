package com.messenger.authandprofile.domain.exception.user;

import com.messenger.authandprofile.shared.exception.NotFoundException;
import lombok.NonNull;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    protected UserNotFoundException(String message) {
        super(message);
    }

    public static @NonNull UserNotFoundException createUserNotFoundByIdException(@NonNull UUID id) {
        return new UserNotFoundException(String.format("User with id '%s' is not found", id));
    }

    public static @NonNull UserNotFoundException createUserNotFoundByLoginException(@NonNull String login) {
        return new UserNotFoundException(String.format("User with login '%s' is not found", login));
    }
}
