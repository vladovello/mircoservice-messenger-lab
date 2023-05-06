package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class MessageId extends AbstractId {
    public MessageId() {
        super();
    }

    public MessageId(@NonNull UUID id) {
        super(id);
    }
}
