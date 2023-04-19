package com.messenger.authandprofile.domain.model.valueobject;

import com.messenger.authandprofile.shared.exception.BusinessRuleViolationException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class RegexPassword implements Password {
    private static final String SPEC_CHARS = "@$!%*?&_.^+=\\-()\\[\\]{}:#";
    private static final String REGEX = String.format("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[%s])[A-Za-z\\d%s]{8,}$", SPEC_CHARS, SPEC_CHARS);
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Getter
    @NonNull
    String value;

    public RegexPassword(@NonNull String value) throws BusinessRuleViolationException {
        final var isMatch = PATTERN.matcher(value).matches();
        if (!isMatch) throw new BusinessRuleViolationException("Given password is invalid");
        this.value = value;
    }
}
