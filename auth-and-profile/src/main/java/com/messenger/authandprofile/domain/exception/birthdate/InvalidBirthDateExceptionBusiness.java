package com.messenger.authandprofile.domain.exception.birthdate;

import com.messenger.authandprofile.shared.exception.BusinessRuleViolationException;
import lombok.NonNull;

import java.time.LocalDate;

public class InvalidBirthDateExceptionBusiness extends BusinessRuleViolationException {
    public InvalidBirthDateExceptionBusiness(@NonNull LocalDate birthDate) {
        super(String.format("Date '%s' is not legal for birth date", birthDate));
    }
}
