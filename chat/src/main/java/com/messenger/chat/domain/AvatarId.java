package com.messenger.chat.domain;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class AvatarId extends AbstractId {
    protected AvatarId(@NonNull UUID id) {
        super(id);
    }
}
