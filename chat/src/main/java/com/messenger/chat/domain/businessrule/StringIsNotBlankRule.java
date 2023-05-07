package com.messenger.chat.domain.businessrule;

import com.messenger.sharedlib.ddd.domain.BusinessRule;

public class StringIsNotBlankRule implements BusinessRule {
    private final String str;
    private final String className;

    public StringIsNotBlankRule(String str, String className) {
        this.str = str;
        this.className = className;
    }

    @Override
    public boolean isComplies() {
        return str.isBlank();
    }

    @Override
    public String ruleViolationMessage() {
        return String.format("'%s' cannot be blank", className);
    }
}
