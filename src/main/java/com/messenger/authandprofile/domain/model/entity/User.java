package com.messenger.authandprofile.domain.model.entity;

import com.messenger.authandprofile.domain.model.valueobject.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

// TODO: 10.04.2023 separate user into Account and Profile. Think of how to deal with common and not related data
// (in registration we'll use email, login, registration date and so on - fields common both for Account and Profile.
// Also we would have full name and city field - the ones, not needed in Account)
// We can implement this using different repositories: AccountRepo and ProfileRepo.
// At the same time, AccountRepo will call the ProfileRepo when saving registered user.
// Editing the profile will throw event which account service will listen to sync its data
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class User {
    // Required fields
    @NonNull private UUID id;
    @NonNull private Login login;
    @NonNull private Email email;
    @NonNull private FullName fullName;
    @NonNull private Password password;
    @NonNull private LocalDate registrationDate;

    // Optional fields
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private String city;
    private UUID avatar;

    protected User(@NonNull Login login, @NonNull Email email, @NonNull FullName fullName, @NonNull Password password) {
        setLogin(login);
        setEmail(email);
        setFullName(fullName);
        setPassword(password);
        setRegistrationDate(LocalDate.now());
    }

    public static @NonNull User.UserBuilder builder(
            @NonNull Login login,
            @NonNull Email email,
            @NonNull FullName fullName,
            @NonNull Password password
    ) {
        return new UserBuilder(login, email, fullName, password);
    }

    public void updateUserProfile(
            @NonNull FullName fullName,
            @NonNull BirthDate birthDate,
            @NonNull PhoneNumber phoneNumber,
            @NonNull String city,
            @NonNull UUID avatar
    ) {
        setFullName(fullName);
        setBirthDate(birthDate);
        setPhoneNumber(phoneNumber);
        setCity(city);
        setAvatar(avatar);
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

    public static class UserBuilder {
        private final User user;

        protected UserBuilder(
                @NonNull Login login,
                @NonNull Email email,
                @NonNull FullName fullName,
                @NonNull Password password
        ) {
            this.user = new User(login, email, fullName, password);
        }

        public UserBuilder withPhoneNumber(PhoneNumber phoneNumber) {
            if (phoneNumber != null) this.user.setPhoneNumber(phoneNumber);
            return this;
        }

        public UserBuilder withBirthDate(BirthDate birthDate) {
            if (birthDate != null) this.user.setBirthDate(birthDate);
            return this;
        }

        public UserBuilder withCity(String city) {
            if (city != null) this.user.setCity(city);
            return this;
        }

        public UserBuilder withAvatar(UUID avatarId) {
            if (avatarId != null) this.user.setAvatar(avatarId);
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
