package com.messenger.authandprofile.domain.exception.phonenumber;

import com.messenger.authandprofile.domain.exception.common.InvalidDataException;

public class InvalidPhoneNumberException extends InvalidDataException {
    public InvalidPhoneNumberException(String phoneNumber) {
        super(String.format("The number '%s' is invalid", phoneNumber));
    }
}
