package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class ChatBlacklistId extends AbstractId {
    public ChatBlacklistId() {
    }

    public ChatBlacklistId(@NonNull UUID id) {
        super(id);
    }
}
