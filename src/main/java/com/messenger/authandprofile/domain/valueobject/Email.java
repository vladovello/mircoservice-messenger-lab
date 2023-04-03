package com.messenger.authandprofile.domain.valueobject;

import lombok.Getter;
import lombok.NonNull;

import java.util.Calendar;
import java.util.regex.Pattern;

public final class Email {
    
    private static final String REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~\\-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~\\-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Getter
    @NonNull
    private final String email;

    public Email(String email) throws IllegalArgumentException {
        final var isMatch = PATTERN.matcher(email).matches();

        if (!isMatch) throw new IllegalArgumentException("Given email is invalid");

        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof Email)) return false;

        Email email1 = (Email) o;

        return getEmail().equals(email1.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
