package com.messenger.chat.domain.chatparticipant;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class ChatParticipantId extends AbstractId {
    protected ChatParticipantId(@NonNull UUID id) {
        super(id);
    }
}
