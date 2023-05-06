package com.messenger.chat.domain.identity;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class UserId extends AbstractId {
    public UserId(@NonNull UUID id) {
        super(id);
    }
}
