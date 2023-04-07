package com.messenger.authandprofile.domain.model.valueobject;

import com.messenger.authandprofile.domain.exception.phonenumber.InvalidPhoneNumberException;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class PhoneNumber {
    String number;

    public PhoneNumber(@NonNull String number) throws InvalidPhoneNumberException {
        final var isMatch = PhoneNumberPattern.PATTERN.matcher(number).matches();
        if (!isMatch) throw new InvalidPhoneNumberException(number);
        this.number = extractPhoneNumber(number);
    }

    private static @NonNull String extractPhoneNumber(@NonNull String number) {
        return number.replaceAll("[+()\\s-]", "");
    }

    private static final class PhoneNumberPattern {
        private static final String REGEX = "^[+]?\\d?[(]?\\d{3}\\)?[-\\s.]?\\d{3}[-\\s.]?\\d{2,3}-?\\d{2,3}$";
        public static final Pattern PATTERN = Pattern.compile(REGEX);
    }
}
