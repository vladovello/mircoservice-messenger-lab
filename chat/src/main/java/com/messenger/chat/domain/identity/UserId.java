package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import java.util.UUID;

public class UserId extends UuidIdentity {
    public UserId(@NonNull UUID id) {
        super(id);
    }
}
