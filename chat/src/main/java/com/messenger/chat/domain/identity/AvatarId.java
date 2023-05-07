package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class AvatarId extends UuidIdentity {
    public AvatarId() {
        super();
    }

    public AvatarId(@NonNull UUID id) {
        super(id);
    }
}
