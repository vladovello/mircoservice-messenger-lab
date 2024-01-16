package com.messenger.chat.domain.chat.valueobject;

import com.messenger.chat.domain.businessrule.StringIsNotBlankRule;
import com.messenger.chat.domain.businessrule.StringIsNotTooLongRule;
import com.messenger.sharedlib.domain.ddd.BusinessRuleViolationException;
import com.messenger.sharedlib.domain.ddd.ValueObject;
import com.messenger.sharedlib.util.Result;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatName extends ValueObject {
    private static final int MAX_LENGTH = 100;

    String name;

    public static Result<ChatName> create(@NonNull String name) {
        try {
            checkRule(new StringIsNotBlankRule(name, "chat name"));
            checkRule(new StringIsNotTooLongRule(name, MAX_LENGTH, "chat name"));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        return Result.success(new ChatName(name));
    }
}
