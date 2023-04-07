package com.messenger.authandprofile.application.auth.exception;

public class PasswordsDoesNotMatchException extends RuntimeException {
    public PasswordsDoesNotMatchException() {
        super("Passwords doesn't match");
    }
}
