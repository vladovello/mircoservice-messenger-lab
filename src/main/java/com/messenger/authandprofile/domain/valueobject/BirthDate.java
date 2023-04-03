package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.InvalidBirthDate;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class BirthDate {
    static final int MIN_AGE = 14;
    static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1900, 1, 10);

    LocalDate date;

    public BirthDate(final LocalDate date) throws InvalidBirthDate {
        if (!isValidBirthDate(date)) throw new InvalidBirthDate(date);
        this.date = date;
    }

    private boolean isValidBirthDate(@NonNull final LocalDate birthDate) {
        return birthDate.isAfter(MIN_BIRTH_DATE) && birthDate.isBefore(LocalDate.now().minusYears(MIN_AGE));
    }
}
