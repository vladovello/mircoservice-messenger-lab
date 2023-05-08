package com.messenger.chat.domain.chatparticipant.valueobject;

import com.messenger.chat.domain.businessrule.StringIsNotBlankRule;
import com.messenger.chat.domain.businessrule.StringIsNotTooLongRule;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.ValueObject;
import com.messenger.sharedlib.util.Result;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoleName extends ValueObject {
    private static final int MAX_LENGTH = 100;

    String name;

    public static @NonNull Result<ChatRoleName> create(@NonNull String name) {
        try {
            checkRule(new StringIsNotBlankRule(name, "chat role name"));
            checkRule(new StringIsNotTooLongRule(name, MAX_LENGTH, "chat role name"));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        return Result.success(new ChatRoleName(name));
    }

    public static @NonNull ChatRoleName createOwnerName() {
        return new ChatRoleName("Owner");
    }

    public static @NonNull ChatRoleName createEveryoneName() {
        return new ChatRoleName("Everyone");
    }
}
