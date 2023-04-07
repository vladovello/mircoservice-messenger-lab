package com.messenger.authandprofile.domain.exception.login;

import com.messenger.authandprofile.shared.exception.InvalidDataException;

public class InvalidLoginException extends InvalidDataException {
    public InvalidLoginException() {
        super(String.format("Login should match the %s", "^[0-9a-zA-Z]\\w{4,32}$"));
    }
}
