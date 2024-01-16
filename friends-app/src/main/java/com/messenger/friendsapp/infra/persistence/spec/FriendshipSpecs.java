package com.messenger.friendsapp.infra.persistence.spec;

import com.google.common.collect.Range;
import com.messenger.friendsapp.infra.persistence.entity.FriendshipEntity;
import com.messenger.friendsapp.infra.persistence.entity.metadata.FriendshipEntityFields;
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
import java.util.UUID;

@Slf4j
public final class FriendshipSpecs {
    private FriendshipSpecs() {
    }

    public static @NonNull Specification<FriendshipEntity> filterFriendships(
            @NonNull UUID userId,
            String fullName,
            Range<LocalDate> additionDate,
            UUID friendId
    ) {
        return createBaseSpec()
                .eq(FriendshipEntityFields.REQUESTER_ID, userId)
                .like(FriendshipEntityFields.FULL_NAME_NAME, fullName)
                .inInterval(
                        FriendshipEntityFields.ADDITION_DATE_NAME,
                        additionDate,
                        LocalDate.MIN,
                        LocalDate.MAX
                )
                .eq(FriendshipEntityFields.ADDRESSEE_ID, friendId);
    }

    @Contract(" -> new")
    private static @NotNull IEntitySpecification<FriendshipEntity> createBaseSpec() {
        return new IgnoreNullEntitySpecification<>(new LoggingEntitySpecification<>(new EntitySpecification<>()));
    }
}
