package com.messenger.sharedlib.infra.util;

import com.google.common.collect.Range;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class EntitySpecificationDecorator<E> implements IEntitySpecification<E> {
    protected final IEntitySpecification<E> entitySpecification;

    protected EntitySpecificationDecorator(IEntitySpecification<E> specification) {
        entitySpecification = specification;
    }

    @Override
    public <T> IEntitySpecification<E> eq(@NotNull String fieldName, T value) {
        return entitySpecification.eq(fieldName, value);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T extends Comparable> IEntitySpecification<E> inInterval(
            @NotNull String fieldName,
            Range<T> interval,
            T defaultLowerBound,
            T defaultUpperBound
    ) {
        return entitySpecification.inInterval(fieldName, interval, defaultLowerBound, defaultUpperBound);
    }

    @Override
    public IEntitySpecification<E> like(@NonNull String fieldName, String value) {
        return entitySpecification.like(fieldName, value);
    }

    @Override
    public Predicate toPredicate(
            @NotNull Root<E> root,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder
    ) {
        return entitySpecification.toPredicate(root, query, criteriaBuilder);
    }
}
