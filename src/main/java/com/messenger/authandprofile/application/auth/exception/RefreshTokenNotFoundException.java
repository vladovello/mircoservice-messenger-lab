package com.messenger.authandprofile.application.auth.exception;

import com.messenger.authandprofile.shared.exception.NotFoundException;

public class RefreshTokenNotFoundException extends NotFoundException {
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
}
