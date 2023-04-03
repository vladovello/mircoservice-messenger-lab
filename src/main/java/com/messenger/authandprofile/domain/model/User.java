package com.messenger.authandprofile.domain.model;

import com.messenger.authandprofile.domain.valueobject.BirthDate;
import com.messenger.authandprofile.domain.valueobject.Email;
import com.messenger.authandprofile.domain.valueobject.Name;
import com.messenger.authandprofile.domain.valueobject.Password;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class User {
    private UUID id;
    private Name name;
    private Email email;
    private Password password;
    private BirthDate birthDate;

    public User(@NonNull Email email, @NonNull Password password) {
        setId();
        setEmail(email);
        setPassword(password);
    }

    public User(@NonNull UUID id, @NonNull Email email, @NonNull Password password) {
        setId(id);
        setEmail(email);
        setPassword(password);
    }

    public User registerUser(Name name, Email email, Password password) {
        setId();
        setName(name);
        setEmail(email);
        setPassword(password);
        return this;
    }

    protected void setId() {
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
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
