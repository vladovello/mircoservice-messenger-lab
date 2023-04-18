package com.messenger.authandprofile.infra.domain.persistence;

import com.google.common.collect.Range;
import com.messenger.authandprofile.application.profile.model.exception.EmptyIntervalException;
import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import com.messenger.authandprofile.infra.domain.persistence.entity.metadata.UserEntityFields;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class UserSpecifications {
    private UserSpecifications() {
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> isLoginMatches(String loginParam) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(UserEntityFields.LOGIN_NAME),
                loginParam
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> searchFullNameLike(String fullNameParam) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get(UserEntityFields.FULL_NAME_NAME),
                "%" + fullNameParam + "%"
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> isPhoneNumberMatches(String phoneNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(UserEntityFields.PHONE_NUMBER_NAME),
                phoneNumber
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> registrationDateInInterval(Range<LocalDate> registrationDate) {
        return getIntervalSpecWithDefaults(
                UserEntityFields.REGISTRATION_DATE_NAME,
                registrationDate,
                LocalDate.MIN,
                LocalDate.MAX
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> birthDateInInterval(Range<LocalDate> birthDate) {
        return getIntervalSpecWithDefaults(
                UserEntityFields.BIRTH_DATE_NAME,
                birthDate,
                BirthDate.getMinBirthDate(),
                BirthDate.getMaxBirthDate()
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> isCityMatches(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserEntityFields.CITY_NAME), city);
    }

    public static @NonNull Specification<UserEntity> filterUsers(@NonNull UserFilterParams userFilterParams) {
        var spec = Specification.<UserEntity>where(null);

        if (userFilterParams.getLogin() != null) {
            spec.and(isLoginMatches(userFilterParams.getLogin().getValue()));
        }
        if (userFilterParams.getFullName() != null) {
            spec.and(searchFullNameLike(userFilterParams.getFullName().getValue()));
        }
        if (userFilterParams.getPhoneNumber() != null) {
            spec.and(isPhoneNumberMatches(userFilterParams.getPhoneNumber().getValue()));
        }
        if (userFilterParams.getRegistrationDate() != null) {
            spec.and(registrationDateInInterval(userFilterParams.getRegistrationDate().getInterval()));
        }
        if (userFilterParams.getBirthDate() != null) {
            spec.and(birthDateInInterval(userFilterParams.getBirthDate().getInterval()));
        }
        if (userFilterParams.getCity() != null) {
            spec.and(isCityMatches(userFilterParams.getCity().getValue()));
        }

        return spec;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Contract(pure = true)
    private static <T extends Comparable> @NonNull Specification<UserEntity> getIntervalSpecWithDefaults(String fieldName,
            Range<T> range,
            @NonNull T lowerBoundDefault,
            @NonNull T upperBoundDefault) {
        return (root, query, criteriaBuilder) -> {
            if (!(range.hasLowerBound() || range.hasUpperBound())) {
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
