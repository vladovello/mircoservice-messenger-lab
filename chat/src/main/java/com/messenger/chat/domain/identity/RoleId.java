package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class RoleId extends AbstractId {
    public RoleId() {
        super();
    }

    public RoleId(@NonNull UUID id) {
        super(id);
    }
}
