package com.messenger.chat.domain;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class ChatId extends AbstractId {
    protected ChatId(@NonNull UUID id) {
        super(id);
    }
}
