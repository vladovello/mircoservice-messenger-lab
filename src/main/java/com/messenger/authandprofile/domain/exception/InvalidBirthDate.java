package com.messenger.authandprofile.domain.exception;

import java.time.LocalDate;

public class InvalidBirthDate extends Exception {
    public InvalidBirthDate(LocalDate birthDate) {
        super(String.format("Date '%s' is not legal for birth date", birthDate.toString()));
    }
}
