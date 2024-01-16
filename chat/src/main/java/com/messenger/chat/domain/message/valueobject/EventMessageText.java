package com.messenger.chat.domain.message.valueobject;

import com.messenger.sharedlib.domain.ddd.ValueObject;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMessageText extends ValueObject {
    private static final int MAX_LENGTH = 100;

    String text;

    public static @NonNull EventMessageText create(@NonNull MessageText messageText) {
        String eventText;

        if (messageText.getText().length() > MAX_LENGTH) {
            eventText = messageText.getText().substring(0, MAX_LENGTH);
        } else {
            eventText = messageText.getText();
        }

        return new EventMessageText(eventText);
    }
}
