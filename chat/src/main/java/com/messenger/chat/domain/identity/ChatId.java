package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class ChatId extends UuidIdentity {
    public ChatId() {
        super();
    }

    public ChatId(@NonNull UUID id) {
        super(id);
    }
}
