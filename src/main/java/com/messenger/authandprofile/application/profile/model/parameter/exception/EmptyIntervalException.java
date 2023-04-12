package com.messenger.authandprofile.application.profile.model.parameter.exception;

public class EmptyIntervalException extends RuntimeException {
    public EmptyIntervalException() {
        super("Interval should not be empty");
    }

    public EmptyIntervalException(String message) {
        super(message);
    }

    public EmptyIntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyIntervalException(Throwable cause) {
        super(cause);
    }

    public EmptyIntervalException(String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
