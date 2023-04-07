package com.messenger.authandprofile.domain.exception.user;

import com.messenger.authandprofile.shared.exception.AlreadyExistsException;
import lombok.NonNull;

import java.util.UUID;

public class UserAlreadyExistsException extends AlreadyExistsException {
    protected UserAlreadyExistsException(String message) {
        super(message);
    }

    public static @NonNull UserAlreadyExistsException createUserIsAlreadyExistsById(@NonNull UUID id) {
        return new UserAlreadyExistsException(String.format("User with id '%s' is already exists", id));
    }

    public static @NonNull UserAlreadyExistsException createUserIsAlreadyExistsByLogin(@NonNull String login) {
        return new UserAlreadyExistsException(String.format("User with login '%s' is already exists", login));
    }

    public static @NonNull UserAlreadyExistsException createUserIsAlreadyExistsByEmail(@NonNull String email) {
        return new UserAlreadyExistsException(String.format("User with email '%s' is already exists", email));
    }
}
