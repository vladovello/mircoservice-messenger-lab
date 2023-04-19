package com.messenger.authandprofile.domain.model.valueobject;

import com.messenger.authandprofile.domain.exception.email.InvalidEmailExceptionBusiness;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Email {
    @Getter String address;

    public Email(@NonNull final String emailString) {
        final var isMatch = EmailPattern.PATTERN.matcher(emailString).matches();
        if (!isMatch) throw new InvalidEmailExceptionBusiness(emailString);
        address = emailString;
    }

    private static final class EmailPattern {
        private static final String NAME = "[a-z0-9!#$%&'*+/=?^_`{|}~\\-]+";
        private static final String REGEX = String.format("(?:%s(?:\\.%s)*+|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*+\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)*+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]?\\d)\\.){3}(?:(2(5[0-5]|[0-4]\\d)|1\\d\\d|[1-9]?\\d)|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", NAME, NAME);
        private static final Pattern PATTERN = Pattern.compile(REGEX);
    }
}
