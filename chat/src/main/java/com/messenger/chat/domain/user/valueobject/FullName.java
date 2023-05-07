package com.messenger.chat.domain.user.valueobject;

import com.messenger.chat.domain.businessrule.StringIsNotBlankRule;
import com.messenger.chat.domain.businessrule.StringIsNotTooLongRule;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.ValueObject;
import com.messenger.sharedlib.util.Result;
import lombok.*;
import org.jetbrains.annotations.Contract;

@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FullName extends ValueObject {
    private static final int MAX_LENGTH = 120;

    @NonNull String firstName;
    String middleName;
    @NonNull String lastName;

    public static @NonNull Result<FullName> create(
            @NonNull String firstName,
            String middleName,
            @NonNull String lastName
    ) {
        var fullName = getFullName(firstName, middleName, lastName);

        try {
            checkRule(new StringIsNotBlankRule(fullName, "full name"));
            checkRule(new StringIsNotTooLongRule(fullName, MAX_LENGTH, "full name"));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        return Result.success(new FullName(firstName, middleName, lastName));
    }

    @Contract(pure = true)
    public static @NonNull String getFullName(@NonNull String firstName, String middleName, @NonNull String lastName) {
        return middleName == null ? firstName + " " + lastName : firstName + " " + middleName + " " + lastName;
    }
}
