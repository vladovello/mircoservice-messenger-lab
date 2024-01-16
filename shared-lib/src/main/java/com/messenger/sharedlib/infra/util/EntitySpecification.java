package com.messenger.sharedlib.infra.util;

import com.google.common.collect.Range;
import com.messenger.sharedlib.parameter.exception.EmptyIntervalException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

@Slf4j
public class EntitySpecification<E> implements IEntitySpecification<E> {
    private final Specification<E> specification;

    public EntitySpecification() {
        specification = Specification.where(null);
    }

    private EntitySpecification(Specification<E> specification) {
        this.specification = specification;
    }

    @Override
    public <T> EntitySpecification<E> eq(@NotNull String fieldName, T value) {
        return new EntitySpecification<>(specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(fieldName),
                value
        )));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T extends Comparable> EntitySpecification<E> inInterval(
            @NotNull String fieldName,
            Range<T> interval,
            T defaultLowerBound,
            T defaultUpperBound
    ) {
        return new EntitySpecification<>(specification.and(getIntervalSpecWithDefaults(
                fieldName,
                interval,
                defaultLowerBound,
                defaultUpperBound
        )));
    }

    @Override
    public EntitySpecification<E> like(@NonNull String fieldName, String value) {
        return new EntitySpecification<>(specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(fieldName)),
                "%" + value.toLowerCase() + "%"
        )));
    }

    @Override
    public Predicate toPredicate(
            @NotNull Root<E> root,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder
    ) {
        return specification.toPredicate(root, query, criteriaBuilder);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Contract(pure = true)
    private <T extends Comparable> @NonNull Specification<E> getIntervalSpecWithDefaults(
            String fieldName,
            Range<T> range,
            @NonNull T lowerBoundDefault,
            @NonNull T upperBoundDefault
    ) {
        return (root, query, criteriaBuilder) -> {
            if (range == null) {
                return null;
            } else if (!(range.hasLowerBound() || range.hasUpperBound())) {
                log.error(String.format("%s: an impossible range has met", LocalDate.now()));
                throw new EmptyIntervalException();
            } else if (!range.hasLowerBound()) {
                return criteriaBuilder.between(root.get(fieldName), lowerBoundDefault, range.upperEndpoint());
            } else if (!range.hasUpperBound()) {
                return criteriaBuilder.between(root.get(fieldName), range.lowerEndpoint(), upperBoundDefault);
            }

            return criteriaBuilder.between(root.get(fieldName), range.lowerEndpoint(), range.upperEndpoint());
        };
    }
}
