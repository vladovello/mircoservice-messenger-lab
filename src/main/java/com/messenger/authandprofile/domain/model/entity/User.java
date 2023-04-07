package com.messenger.authandprofile.domain.model.entity;

import com.messenger.authandprofile.domain.model.valueobject.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
public class User {
    // Required fields
    private UUID id;
    private Login login;
    private Email email;
    private FullName fullName;
    private Password password;
    private LocalDate registrationDate;

    // Optional fields
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private String city;
    private UUID avatarId;

    protected User(
            @NonNull Login login,
            @NonNull Email email,
            @NonNull FullName fullName,
            @NonNull Password password
    ) {
        setLogin(login);
        setEmail(email);
        setFullName(fullName);
        setPassword(password);
        setRegistrationDate(LocalDate.now());
    }

    // TODO: 08.04.2023 подумать, что лучше: использовать билдер или добавить сеттеры для опциональных полей
    public static @NonNull User registerUser(
            @NonNull Login login,
            @NonNull Email email,
            @NonNull FullName fullName,
            @NonNull Password password
    ) {
        return new User(login, email, fullName, password);
    }

    public static @NonNull User reconstructUser(
            @NonNull UUID id,
            @NonNull Login login,
            @NonNull Email email,
            @NonNull FullName fullName,
            @NonNull Password password
    ) {
        var user = new User(login, email, fullName, password);
        user.setId(id);
        return user;
    }

    protected void generateId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public static class Builder {
        private final User user;

        public Builder(
                @NonNull Login login,
                @NonNull Email email,
                @NonNull FullName fullName,
                @NonNull Password password
        ) {
            this.user = new User(login, email, fullName, password);
        }

        public Builder withPhoneNumber(PhoneNumber phoneNumber) {
            if (phoneNumber != null) this.user.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder withCity(String city) {
            if (city != null) this.user.setCity(city);
            return this;
        }

        public Builder withAvatar(UUID avatarId) {
            if (avatarId != null) this.user.setAvatarId(avatarId);
            return this;
        }

        public User registerUser() {
            this.user.generateId();
            return this.user;
        }

        public User reconstructUser(UUID id) {
            this.user.setId(id);
            return this.user;
        }
    }
}
