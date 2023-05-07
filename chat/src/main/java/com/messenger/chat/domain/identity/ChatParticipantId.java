package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class ChatParticipantId extends UuidIdentity {
    public ChatParticipantId() {
        super();
    }

    public ChatParticipantId(@NonNull UUID id) {
        super(id);
    }
}
