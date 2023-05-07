package com.messenger.sharedlib.ddd.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class AbstractId<T> implements Identity<T> {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private T id;

    protected AbstractId() {
        this.setId(generateId());
    }

    protected AbstractId(@NonNull T id) {
        this.setId(id);
    }

    protected abstract T generateId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractId)) return false;

        var that = (AbstractId<?>) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
