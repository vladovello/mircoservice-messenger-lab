package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.InvalidBirthDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {
    @Test
    void when_BirthDateIsInValidRange_Expect_ObjectCreating() {
        try {
            var date = LocalDate.of(2000, 1, 1);
            var birthDate = new BirthDate(date);
            assertEquals(birthDate.getDate(), date);
        } catch (InvalidBirthDate e) {
            fail();
        }
    }

    @Test
    void when_BirthDateIsTooOld_Except_ThrowingException() {
        var date = LocalDate.of(1890, 1, 1);
        assertThrows(InvalidBirthDate.class, () -> new BirthDate(date));
    }

    @Test
    void when_BirthDateIsTooYoung_Except_ThrowingException() {
        var date = LocalDate.now();
        assertThrows(InvalidBirthDate.class, () -> new BirthDate(date));
    }

    @Test
    void when_BirthDateIsEqualsToLowerBound_Except_ObjectCreating() {
        var date = BirthDate.MIN_BIRTH_DATE;
        assertThrows(InvalidBirthDate.class, () -> new BirthDate(date));
    }
}