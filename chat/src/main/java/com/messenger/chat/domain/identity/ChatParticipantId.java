package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class ChatParticipantId extends AbstractId {
    public ChatParticipantId() {
        super();
    }

    public ChatParticipantId(@NonNull UUID id) {
        super(id);
    }
}
