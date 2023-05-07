package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class MessageId extends UuidIdentity {
    public MessageId() {
        super();
    }

    public MessageId(@NonNull UUID id) {
        super(id);
    }
}
