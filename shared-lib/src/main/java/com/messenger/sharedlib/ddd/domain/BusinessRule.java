package com.messenger.sharedlib.ddd.domain;

public interface BusinessRule {
    boolean isComplies();
    String ruleViolationMessage();
}
