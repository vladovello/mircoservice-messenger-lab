package com.messenger.authandprofile.domain.exception.email;

import com.messenger.authandprofile.shared.exception.BusinessRuleViolationException;

public class InvalidEmailExceptionBusiness extends BusinessRuleViolationException {
    public InvalidEmailExceptionBusiness(String emailAddress) {
        super(String.format("Address '%s' is not legal for email", emailAddress));
    }
}
