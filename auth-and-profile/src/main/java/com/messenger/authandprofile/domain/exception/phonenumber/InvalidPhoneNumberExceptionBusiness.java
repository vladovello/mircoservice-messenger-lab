package com.messenger.authandprofile.domain.exception.phonenumber;

import com.messenger.authandprofile.shared.exception.BusinessRuleViolationException;

public class InvalidPhoneNumberExceptionBusiness extends BusinessRuleViolationException {
    public InvalidPhoneNumberExceptionBusiness(String phoneNumber) {
        super(String.format("The phone number '%s' is invalid", phoneNumber));
    }
}
