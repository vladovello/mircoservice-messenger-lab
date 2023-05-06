package com.messenger.chat.domain.user;

import com.messenger.chat.domain.identity.AvatarId;
import com.messenger.chat.domain.identity.UserId;
import com.messenger.chat.domain.user.valueobject.FullName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

@Getter
@Setter(AccessLevel.PRIVATE)
public class User {
    @NonNull private UserId userId;
    @NonNull private FullName fullName;
    @NonNull private AvatarId avatarId;

    protected User(@NonNull UserId userId, @NonNull FullName fullName, @NonNull AvatarId avatarId) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarId = avatarId;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NonNull User reconstruct(@NonNull UserId userId, @NonNull FullName fullName, @NonNull AvatarId avatarId) {
        return new User(userId, fullName, avatarId);
    }
}
