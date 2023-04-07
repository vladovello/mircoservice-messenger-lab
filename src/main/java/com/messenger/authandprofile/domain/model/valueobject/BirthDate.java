package com.messenger.authandprofile.domain.model.valueobject;

import com.messenger.authandprofile.domain.exception.birthdate.InvalidBirthDateException;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class BirthDate {
    static final short MIN_AGE = 14;
    static final short MAX_AGE = 150;

    LocalDate date;

    public BirthDate(@NonNull final LocalDate date) throws InvalidBirthDateException {
        if (!isValidBirthDate(date)) throw new InvalidBirthDateException(date);
        this.date = date;
    }

    private boolean isValidBirthDate(@NonNull final LocalDate birthDate) {
        var now = LocalDate.now();
        return !birthDate.isBefore(now.minusYears(MAX_AGE)) && !birthDate.isAfter(now.minusYears(MIN_AGE));
    }
}
