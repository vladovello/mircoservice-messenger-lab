package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.application.profile.model.parameter.param.DiscreteParam;
import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    private UserSpecifications() {
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> isLoginMatches(DiscreteParam<String> loginParam) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("login"), loginParam.getValue());
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NonNull Specification<UserEntity> searchFullNameLike(DiscreteParam<String> fullNameParam) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get("fullName"),
                "%" + fullNameParam.getValue() + "%"
        );
    }
}
