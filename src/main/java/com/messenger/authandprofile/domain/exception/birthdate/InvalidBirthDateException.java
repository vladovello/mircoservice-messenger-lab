package com.messenger.authandprofile.domain.exception.birthdate;

import com.messenger.authandprofile.shared.exception.InvalidDataException;
import lombok.NonNull;

import java.time.LocalDate;

public class InvalidBirthDateException extends InvalidDataException {
    public InvalidBirthDateException(@NonNull LocalDate birthDate) {
        super(String.format("Date '%s' is not legal for birth date", birthDate));
    }
}
