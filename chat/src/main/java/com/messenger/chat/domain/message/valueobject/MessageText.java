package com.messenger.chat.domain.message.valueobject;

import com.messenger.chat.domain.businessrule.StringIsNotBlankRule;
import com.messenger.chat.domain.businessrule.StringIsNotTooLongRule;
import com.messenger.sharedlib.domain.ddd.BusinessRuleViolationException;
import com.messenger.sharedlib.domain.ddd.ValueObject;
import com.messenger.sharedlib.util.Result;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageText extends ValueObject {
    private static final int MAX_LENGTH = 1500;

    String text;

    public static @NonNull Result<MessageText> create(@NonNull String text) {
        try {
            checkRule(new StringIsNotBlankRule(text, MessageText.class.getName()));
            checkRule(new StringIsNotTooLongRule(text, MAX_LENGTH, MessageText.class.getName()));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        return Result.success(new MessageText(text));
    }
}
