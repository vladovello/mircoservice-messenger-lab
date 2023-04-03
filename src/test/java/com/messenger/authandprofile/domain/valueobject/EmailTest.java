package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.InvalidEmail;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {
    @SneakyThrows
    @Test
    void when_EmailIsValid_Expect_AddressesEquals() {
        var validEmail = "abc12@ds212.com";
        var email = new Email(validEmail);
        assertEquals(email.getAddress(), validEmail);
    }

    @Test
    void when_EmailIsNotValid_Expect_ThrowException() {
        var invalidEmail = "_as@__as.c";
        assertThrows(InvalidEmail.class, () -> new Email(invalidEmail));
    }

    @Test
    void when_EmailIsEmpty_Expect_ThrowException() {
        var empty = "";
        assertThrows(InvalidEmail.class, () -> new Email(empty));
    }
}