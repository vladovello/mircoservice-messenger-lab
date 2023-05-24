package com.messenger.friendsapp.infra.persistence.spec;

import com.google.common.collect.Range;
import com.messenger.friendsapp.infra.persistence.entity.FriendshipEntity;
import com.messenger.friendsapp.infra.persistence.entity.metadata.FriendshipEntityFields;
import com.messenger.sharedlib.parameter.exception.EmptyIntervalException;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public final class FriendshipEntitySpecs {
    private FriendshipEntitySpecs() {
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<FriendshipEntity> searchFullNameLike(String fullName) {
        logSpecCreation("fullName");

        // TODO: 21.04.2023 case independent search
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(FriendshipEntityFields.FULL_NAME_NAME)),
                "%" + fullName.toLowerCase() + "%"
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<FriendshipEntity> additionDateInInterval(Range<LocalDate> additionDate) {
        logSpecCreation("additionDate");

        return getIntervalSpecWithDefaults(
                FriendshipEntityFields.ADDITION_DATE_NAME,
                additionDate,
                LocalDate.MIN,
                LocalDate.MAX
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<FriendshipEntity> isRequesterIdMatches(UUID requesterId) {
        logSpecCreation("requesterId");

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(FriendshipEntityFields.REQUESTER_ID),
                requesterId
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<FriendshipEntity> isAddresseeIdMatches(UUID addresseeId) {
        logSpecCreation("addresseeId");

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(FriendshipEntityFields.ADDRESSEE_ID),
                addresseeId
        );
    }

    public static @NonNull Specification<FriendshipEntity> filterFriendships(
            @NonNull UUID userId,
            DiscreteParam<String> fullName,
            IntervalParam<LocalDate> additionDate,
            DiscreteParam<UUID> friendId
    ) {
        var spec = Specification.where(isRequesterIdMatches(userId));

        if (fullName != null) {
            spec = spec.and(searchFullNameLike(fullName.getValue()));
        }
        if (additionDate != null) {
            spec = spec.and(additionDateInInterval(additionDate.getInterval()));
        }
        if (friendId != null) {
            spec = spec.and(isAddresseeIdMatches(friendId.getValue()));
        }

        return spec;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Contract(pure = true)
    private static <T extends Comparable> @NonNull Specification<FriendshipEntity> getIntervalSpecWithDefaults(
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

    private static void logSpecCreation(String specName) {
        log.info(String.format("Creating '%s' specification", specName));
    }
}
