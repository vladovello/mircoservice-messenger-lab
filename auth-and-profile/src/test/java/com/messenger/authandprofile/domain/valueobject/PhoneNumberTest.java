package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.phonenumber.InvalidPhoneNumberExceptionBusiness;
import com.messenger.authandprofile.domain.model.valueobject.PhoneNumber;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {
    @SneakyThrows
    @Test
    void when_NumberIsValid_Expect_PhoneNumberCreatedAndOnlyDigitsLeft() {
        var number = "+7(999)999-99-99";
        var phoneNumber = new PhoneNumber(number);
        var expectedNumber = "79999999999";
        assertEquals(expectedNumber, phoneNumber.getNumber());
    }

    @Test
    void when_NumberIsInvalid_Expect_ThrowException() {
        var number = "+7(999)9";
        assertThrows(InvalidPhoneNumberExceptionBusiness.class, () -> new PhoneNumber(number));
    }
}