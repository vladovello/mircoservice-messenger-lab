package com.messenger.authandprofile.domain.valueobject;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Password {
    private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Getter
    @NonNull
    String value;

    public Password(String value) throws IllegalArgumentException {
        final var isMatch = PATTERN.matcher(value).matches();

        if (!isMatch) throw new IllegalArgumentException("Given password is invalid");

        this.value = value;
    }
}
