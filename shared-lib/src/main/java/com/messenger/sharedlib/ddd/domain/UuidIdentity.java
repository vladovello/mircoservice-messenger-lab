package com.messenger.sharedlib.ddd.domain;

import java.util.UUID;

public class UuidIdentity extends AbstractId<UUID> {
    public UuidIdentity() {
        super();
    }

    public UuidIdentity(UUID id) {
        super(id);
    }

    @Override
    protected UUID generateId() {
        return UUID.randomUUID();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
