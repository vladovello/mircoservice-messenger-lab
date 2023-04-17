package com.messenger.authandprofile.domain.exception.phonenumber;

import com.messenger.authandprofile.shared.exception.InvalidDataException;

public class InvalidPhoneNumberException extends InvalidDataException {
    public InvalidPhoneNumberException(String phoneNumber) {
        super(String.format("The phone number '%s' is invalid", phoneNumber));
    }
}
