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
    private Password basicPassword;
    private LocalDate registrationDate;

    // Optional fields
    private FullName fullName;
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private String city;
    private UUID avatarId;

    protected User(@NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
        setId();
        setLogin(login);
        setEmail(email);
        setBasicPassword(basicPassword);
        setRegistrationDate(LocalDate.now());
    }

    protected User(@NonNull UUID id, @NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
        setId(id);
        setLogin(login);
        setEmail(email);
        setBasicPassword(basicPassword);
        setRegistrationDate(LocalDate.now());
    }

    public static class Builder {
        private final User user;

        public Builder(@NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
            this.user = new User(login, email, basicPassword);
        }

        public Builder(@NonNull UUID id, @NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
            this.user = new User(id, login, email, basicPassword);
        }

        public Builder withFullName(@NonNull FullName fullName) {
            this.user.setFullName(fullName);
            return this;
        }

        public Builder withPhoneNumber(@NonNull PhoneNumber phoneNumber) {
            this.user.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder withCity(@NonNull String city) {
            this.user.setCity(city);
            return this;
        }

        public Builder withAvatar(@NonNull UUID avatarId) {
            this.user.setAvatarId(avatarId);
            return this;
        }

        public User registerUser() {
            return this.user;
        }

        public User reconstructUser() {
            return this.user;
        }
    }


    // TODO: 08.04.2023 подумать, что лучше: использовать билдер или добавить сеттеры для опциональных полей 
    public static @NonNull User registerUser(@NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
        return new User(login, email, basicPassword);
    }

    public static @NonNull User reconstructUser(@NonNull UUID id, @NonNull Login login, @NonNull Email email, @NonNull Password basicPassword) {
        return new User(id, login, email, basicPassword);
    }

    protected void setId() {
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
}
