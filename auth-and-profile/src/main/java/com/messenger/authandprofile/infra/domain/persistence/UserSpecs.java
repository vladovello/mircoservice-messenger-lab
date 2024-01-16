package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import com.messenger.authandprofile.infra.domain.persistence.entity.metadata.UserEntityFields;
import com.messenger.sharedlib.infra.util.EntitySpecification;
import com.messenger.sharedlib.infra.util.IEntitySpecification;
import com.messenger.sharedlib.infra.util.IgnoreNullEntitySpecification;
import com.messenger.sharedlib.infra.util.LoggingEntitySpecification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Slf4j
public final class UserSpecs {
    private UserSpecs() {
    }

    public static @NonNull Specification<UserEntity> filterUsers(@NonNull UserFilterParams params) {
        return createBaseSpec()
                .eq(UserEntityFields.LOGIN, params.getLoginValue())
                .like(UserEntityFields.FULL_NAME, params.getFullNameValue())
                .eq(UserEntityFields.PHONE_NUMBER, params.getPhoneNumberValue())
                .inInterval(
                        UserEntityFields.REGISTRATION_DATE,
                        params.getRegistrationDateInterval(),
                        LocalDate.MIN,
                        LocalDate.MAX
                )
                .inInterval(
                        UserEntityFields.BIRTH_DATE,
                        params.getBirthDateInterval(),
                        BirthDate.getMinBirthDate(),
                        BirthDate.getMaxBirthDate()
                )
                .eq(UserEntityFields.CITY, params.getCityValue());
    }

    @Contract(" -> new")
    private static @NotNull IEntitySpecification<UserEntity> createBaseSpec() {
        return new IgnoreNullEntitySpecification<>(new LoggingEntitySpecification<>(new EntitySpecification<>()));
    }
}
