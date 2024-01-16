package com.messenger.sharedlib.domain.ddd;

import lombok.NonNull;

public abstract class BusinessRuleValidator {
    protected BusinessRuleValidator() {
    }

    protected static void checkRule(@NonNull BusinessRule businessRule) throws BusinessRuleViolationException {
        if (!businessRule.isComplies()) {
            throw new BusinessRuleViolationException(businessRule);
        }
    }
}
