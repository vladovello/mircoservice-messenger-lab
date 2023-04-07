package com.messenger.authandprofile.domain.exception.user;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Contract("_ -> fail")
    public static void throwUserNotFoundByIdException(@NonNull UUID id) {
        throw new UserNotFoundException(String.format("User with id '%s' is not found", id));
    }

    @Contract("_ -> fail")
    public static void throwUserIsAlreadyExistsById(@NonNull UUID id) {
        throw new UserNotFoundException(String.format("User with id '%s' is already exists", id));
    }

    @Contract("_ -> fail")
    public static void throwUserIsAlreadyExistsByLogin(@NonNull String login) {
        throw new UserNotFoundException(String.format("User with login '%s' is already exists", login));
    }

    @Contract("_ -> fail")
    public static void throwUserIsAlreadyExistsByEmail(@NonNull String email) {
        throw new UserNotFoundException(String.format("User with email '%s' is already exists", email));
    }
}
