package com.messenger.authandprofile.infra.auth.jwt.validator;

public interface TokenValidator {
    boolean validate(String token);
}
