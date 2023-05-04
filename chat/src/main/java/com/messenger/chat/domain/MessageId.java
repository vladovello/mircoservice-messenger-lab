package com.messenger.chat.domain;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class MessageId extends AbstractId {
    protected MessageId(@NonNull UUID id) {
        super(id);
    }
}
