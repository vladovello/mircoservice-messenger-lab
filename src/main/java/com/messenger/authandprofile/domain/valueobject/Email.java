package com.messenger.authandprofile.domain.valueobject;

import com.messenger.authandprofile.domain.exception.InvalidEmail;
import lombok.Getter;
import lombok.NonNull;

import java.util.regex.Pattern;

public final class Email {
    @Getter
    private final String address;

    public Email(@NonNull final String emailString) throws InvalidEmail {
        final var isMatch = EmailPattern.PATTERN.matcher(emailString).matches();

        if (!isMatch) throw new InvalidEmail(emailString);

        address = emailString;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Email)) {
            return false;
        }

        Email email1 = (Email) o;

        return getAddress().equals(email1.getAddress());
    }

    @Override
    public int hashCode() {
        return getAddress().hashCode();
    }

    private static final class EmailPattern {
        private static final String NAME = "[a-z0-9!#$%&'*+/=?^_`{|}~\\-]+";
        private static final String REGEX = String.format("(?:%s(?:\\.%s)*+|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*+\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)*+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]?\\d)\\.){3}(?:(2(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]?\\d)|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", NAME, NAME);
        private static final Pattern PATTERN = Pattern.compile(REGEX);
    }
}
