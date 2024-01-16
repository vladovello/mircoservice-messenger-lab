package com.messenger.sharedlib.domain.ddd;

public interface BusinessRule {
    boolean isComplies();
    String ruleViolationMessage();
}
