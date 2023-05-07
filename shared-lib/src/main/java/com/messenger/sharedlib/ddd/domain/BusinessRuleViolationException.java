package com.messenger.sharedlib.ddd.domain;

import lombok.NonNull;

public class BusinessRuleViolationException extends Exception {
    public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(@NonNull BusinessRule businessRule) {
        super(businessRule.ruleViolationMessage());
    }
}
