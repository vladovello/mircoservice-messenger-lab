package com.messenger.sharedlib.infra.util;

import com.google.common.collect.Range;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

public interface IEntitySpecification<E> extends Specification<E> {
    <T> IEntitySpecification<E> eq(@NonNull String fieldName, T value);

    @SuppressWarnings("rawtypes")
    <T extends Comparable> IEntitySpecification<E> inInterval(
            @NonNull String fieldName,
            Range<T> interval,
            T defaultLowerBound,
            T defaultUpperBound
    );

    IEntitySpecification<E> like(@NonNull String fieldName, String value);
}
