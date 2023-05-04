package com.messenger.chat.domain;

import com.messenger.sharedlib.ddd.domain.AbstractId;
import lombok.NonNull;

import java.util.UUID;

public class UserId extends AbstractId {
    protected UserId(@NonNull UUID id) {
        super(id);
    }
}
