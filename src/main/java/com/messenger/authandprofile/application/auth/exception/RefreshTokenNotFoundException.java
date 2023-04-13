package com.messenger.authandprofile.application.auth.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("The refresh token was not found");
    }

    public RefreshTokenNotFoundException(String refreshToken) {
        super(String.format("The refresh token '%s' was not found", refreshToken));
    }

    public RefreshTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenNotFoundException(String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
