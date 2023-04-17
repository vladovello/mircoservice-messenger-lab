package com.messenger.authandprofile.application.auth.exception;

public class RefreshTokenReuseException extends RuntimeException {
    public RefreshTokenReuseException() {
        super();
    }

    public RefreshTokenReuseException(String message) {
        super(message);
    }

    public RefreshTokenReuseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenReuseException(Throwable cause) {
        super(cause);
    }
}
