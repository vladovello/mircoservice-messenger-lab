package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class RoleId extends UuidIdentity {
    public RoleId() {
        super();
    }

    public RoleId(@NonNull UUID id) {
        super(id);
    }
}
