package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.model.valueobject.RegexPassword;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegexPasswordTest {
    @Test
    void when_PasswordIsValid_Expect_PasswordsEquals() {
        var validPassword = "as!A33dsf";
        var password = new RegexPassword(validPassword);
        assertEquals(password.getValue(), validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = { "as!A33", "AS!A33SF", "as!a33sf", "As1a33Sf" })
    void when_PasswordIsInvalid_Expect_ThrowException(String invalidPassword) {
        assertThrows(IllegalArgumentException.class, () -> new RegexPassword(invalidPassword));
    }
}