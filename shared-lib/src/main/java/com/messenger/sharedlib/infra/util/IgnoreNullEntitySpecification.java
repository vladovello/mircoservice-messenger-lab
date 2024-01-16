package com.messenger.sharedlib.infra.util;

import com.google.common.collect.Range;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public class IgnoreNullEntitySpecification<E> extends EntitySpecificationDecorator<E> {
    public IgnoreNullEntitySpecification(IEntitySpecification<E> specification) {
        super(specification);
    }

    @Override
    public <T> IEntitySpecification<E> eq(@NotNull String fieldName, T value) {
        if (value == null) return entitySpecification;
        return super.eq(fieldName, value);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T extends Comparable> IEntitySpecification<E> inInterval(
            @NotNull String fieldName,
            Range<T> interval,
            T defaultLowerBound,
            T defaultUpperBound
    ) {
        if (interval == null) return entitySpecification;
        return super.inInterval(fieldName, interval, defaultLowerBound, defaultUpperBound);
    }

    @Override
    public IEntitySpecification<E> like(@NonNull String fieldName, String value) {
        if (value == null) return entitySpecification;
        return super.like(fieldName, value);
    }
}
