package com.messenger.authandprofile.application.profile.exception;

public class EmptyIntervalException extends IntervalException {
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
}
