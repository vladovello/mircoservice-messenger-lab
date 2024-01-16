package com.messenger.chat.domain.businessrule;

import com.messenger.sharedlib.domain.ddd.BusinessRule;

public class StringIsNotTooLongRule implements BusinessRule {
    private final String str;
    private final String className;
    private final int maxLength;

    public StringIsNotTooLongRule(String str, int maxLength, String className) {
        this.str = str;
        this.className = className;
        this.maxLength = maxLength;
    }

    @Override
    public boolean isComplies() {
        return str.length() <= maxLength;
    }

    @Override
    public String ruleViolationMessage() {
        return String.format("%d is to long for a '%s'. Maximum is %d", str.length(), className, maxLength);
    }
}
