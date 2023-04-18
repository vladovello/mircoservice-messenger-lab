package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.birthdate.InvalidBirthDateException;
import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BirthDateTest {
    @ParameterizedTest
    @ValueSource(ints = {20, 14, 150})
    void when_BirthDateIsInValidRange_Expect_ObjectCreating(int age) {
        var date = LocalDate.now().minusYears(age);
        assertDoesNotThrow(() -> new BirthDate(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 151})
    void when_BirthDateIsOutOfRange_Except_ThrowingException(int age) {
        var date = LocalDate.now().minusYears(age);
        assertThrows(InvalidBirthDateException.class, () -> new BirthDate(date));
    }
}