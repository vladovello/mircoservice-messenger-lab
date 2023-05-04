package com.messenger.chat.domain.exception;

public class BusinessRuleViolationException extends Exception {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
