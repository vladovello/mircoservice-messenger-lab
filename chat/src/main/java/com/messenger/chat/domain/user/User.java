package com.messenger.chat.domain.user;

import com.messenger.chat.domain.user.converter.FullNameConverter;
import com.messenger.chat.domain.user.valueobject.FullName;
import com.messenger.sharedlib.ddd.domain.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class User extends DomainEntity {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID userId;
    @Column(nullable = false)
    @Convert(converter = FullNameConverter.class)
    @NonNull
    private FullName fullName;
    @Column(nullable = false)
    @NonNull
    private UUID avatarId;

    protected User(@NonNull UUID userId, @NonNull FullName fullName, @NonNull UUID avatarId) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarId = avatarId;
    }

    protected User() {
        // For JPA
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull User reconstruct(
            @NonNull UUID userId,
            @NonNull FullName fullName,
            @NonNull UUID avatarId
    ) {
        return new User(userId, fullName, avatarId);
    }
}
