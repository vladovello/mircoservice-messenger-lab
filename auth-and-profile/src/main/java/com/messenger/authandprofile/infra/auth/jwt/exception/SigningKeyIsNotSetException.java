package com.messenger.authandprofile.infra.auth.jwt.exception;

public class SigningKeyIsNotSetException extends RuntimeException {
    public SigningKeyIsNotSetException() {
        super("No signing key is set to JwtBearerTokenParameters");
    }

    public SigningKeyIsNotSetException(String message) {
        super(message);
    }

    public SigningKeyIsNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SigningKeyIsNotSetException(Throwable cause) {
        super(cause);
    }

    public SigningKeyIsNotSetException(String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
