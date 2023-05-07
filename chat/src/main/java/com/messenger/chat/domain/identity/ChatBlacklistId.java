package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class ChatBlacklistId extends UuidIdentity {
    public ChatBlacklistId() {
    }

    public ChatBlacklistId(@NonNull UUID id) {
        super(id);
    }
}
