package com.messenger.authandprofile.shared.exception;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException() {
    }

    public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuleViolationException(Throwable cause) {
        super(cause);
    }

    public BusinessRuleViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
