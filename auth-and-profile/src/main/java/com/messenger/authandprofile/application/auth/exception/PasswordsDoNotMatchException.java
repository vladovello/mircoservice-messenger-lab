package com.messenger.authandprofile.application.auth.exception;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Passwords doesn't match");
    }
}
