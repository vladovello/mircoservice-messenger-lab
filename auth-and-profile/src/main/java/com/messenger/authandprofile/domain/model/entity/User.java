package com.messenger.authandprofile.domain.model.entity;

import com.messenger.authandprofile.domain.event.ProfileChangedEvent;
import com.messenger.authandprofile.domain.event.UserCreatedEvent;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.sharedlib.domain.ddd.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @NonNull private List<DomainEvent> domainEvents = new ArrayList<>();

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

        domainEvents.add(new ProfileChangedEvent(id, fullName, birthDate, avatar));
    }

    public void clearEvents() {
        this.domainEvents.clear();
    }

    protected void generateId() {
        this.id = UUID.randomUUID();
    }

    protected void setRegistrationDate() {
        this.registrationDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id.equals(user.id);
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

        // TODO: 20.04.2023 move registration date out of domain model. Work with it using events
        // TODO: 22.05.2023 think about moving User creation into domain service since User needs default avatar
        public User registerUser() {
            this.user.generateId();
            this.user.setRegistrationDate();
            this.user.setAvatar(UUID.nameUUIDFromBytes(new byte[0]));

            this.user.domainEvents.add(new UserCreatedEvent(
                    this.user.getId(),
                    this.user.getFullName(),
                    this.user.getBirthDate(),
                    this.user.getAvatar()
            ));

            return this.user;
        }

        public User reconstructUser(UUID id, LocalDate registrationDate) {
            this.user.setId(id);
            this.user.setRegistrationDate(registrationDate);
            return this.user;
        }
    }
}
