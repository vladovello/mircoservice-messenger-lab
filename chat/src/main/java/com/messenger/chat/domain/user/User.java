package com.messenger.chat.domain.user;

import com.messenger.chat.domain.identity.AvatarId;
import com.messenger.chat.domain.identity.UserId;
import com.messenger.chat.domain.identity.converter.UuidConverter;
import com.messenger.chat.domain.user.converter.FullNameConverter;
import com.messenger.chat.domain.user.valueobject.FullName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private UserId userId;
    @Column(nullable = false)
    @Convert(converter = FullNameConverter.class)
    @NonNull
    private FullName fullName;
    @Column(nullable = false)
    @Convert(converter = UuidConverter.class)
    @NonNull
    private AvatarId avatarId;

    protected User(@NonNull UserId userId, @NonNull FullName fullName, @NonNull AvatarId avatarId) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarId = avatarId;
    }

    protected User() {
        // For JPA
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull User reconstruct(
            @NonNull UserId userId,
            @NonNull FullName fullName,
            @NonNull AvatarId avatarId
    ) {
        return new User(userId, fullName, avatarId);
    }
}
