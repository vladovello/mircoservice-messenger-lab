package com.messenger.sharedlib.infra.util;

import com.google.common.collect.Range;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class LoggingEntitySpecification<E> extends EntitySpecificationDecorator<E> {
    public LoggingEntitySpecification(IEntitySpecification<E> specification) {
        super(specification);
    }

    private static void logSpecCreation(String specName) {
        log.info(String.format("Creating '%s' specification", specName));
    }

    @Override
    public <T> IEntitySpecification<E> eq(@NotNull String fieldName, T value) {
        logSpecCreation(fieldName);
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
        logSpecCreation(fieldName);
        return super.inInterval(fieldName, interval, defaultLowerBound, defaultUpperBound);
    }

    @Override
    public IEntitySpecification<E> like(@NonNull String fieldName, String value) {
        logSpecCreation(fieldName);
        return super.like(fieldName, value);
    }
}
