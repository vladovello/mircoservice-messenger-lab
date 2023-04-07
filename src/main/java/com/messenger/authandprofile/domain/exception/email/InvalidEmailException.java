package com.messenger.authandprofile.domain.exception.email;

import com.messenger.authandprofile.shared.exception.InvalidDataException;

public class InvalidEmailException extends InvalidDataException {
    public InvalidEmailException(String emailAddress) {
        super(String.format("Address '%s' is not legal for email", emailAddress));
    }
}
