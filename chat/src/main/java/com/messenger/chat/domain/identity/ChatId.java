package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class ChatId extends AbstractId {
    public ChatId() {
        super();
    }

    public ChatId(@NonNull UUID id) {
        super(id);
    }
}
