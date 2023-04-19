package com.messenger.authandprofile.domain.exception.login;

import com.messenger.authandprofile.shared.exception.BusinessRuleViolationException;

public class InvalidLoginExceptionBusiness extends BusinessRuleViolationException {
    public InvalidLoginExceptionBusiness() {
        super(String.format("Login should match the %s", "^[0-9a-zA-Z]\\w{4,32}$"));
    }
}
