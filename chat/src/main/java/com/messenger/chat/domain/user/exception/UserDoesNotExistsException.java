package com.messenger.chat.domain.user.exception;

import com.messenger.sharedlib.exception.NotFoundException;

import java.util.UUID;

public class UserDoesNotExistsException extends NotFoundException {
    public UserDoesNotExistsException(UUID userId) {
        super(String.format("User with id %s does not exists", userId));
    }
}
