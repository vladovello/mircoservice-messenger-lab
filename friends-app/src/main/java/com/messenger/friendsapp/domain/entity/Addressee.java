package com.messenger.friendsapp.domain.entity;

import com.messenger.friendsapp.domain.valueobject.FullName;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

@Getter
public class Addressee {
    @NonNull private UUID id;
    @NonNull private FullName fullName;

    private Addressee(@NonNull UUID id, @NonNull FullName fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NonNull Addressee createNewAddressee(@NonNull UUID id, @NonNull FullName fullName) {
        return new Addressee(id, fullName);
    }

    public static @NonNull Addressee reconstructAddressee(@NonNull UUID id, @NonNull FullName fullName) {
        return new Addressee(id, fullName);
    }
}
