package com.messenger.sharedlib.ddd.domain;

import lombok.NonNull;

import java.util.UUID;

public abstract class AbstractId implements Identity {
    private static final long serialVersionUID = 1L;

    private UUID id;

    protected AbstractId() {
        this.setId(UUID.randomUUID());
    }

    protected AbstractId(@NonNull UUID id) {
        this.setId(id);
    }

    @Override
    public UUID getId() {
        return id;
    }

    protected void setId(@NonNull UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractId)) return false;

        AbstractId that = (AbstractId) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
