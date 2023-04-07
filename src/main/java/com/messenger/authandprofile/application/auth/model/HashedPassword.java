package com.messenger.authandprofile.application.auth.model;

import com.google.common.hash.Hashing;
import com.messenger.authandprofile.domain.model.valueobject.Password;

import java.nio.charset.StandardCharsets;

public class HashedPassword implements Password {
    private final Password password;

    public HashedPassword(Password password) {
        this.password = password;
    }

    @Override
    public String getValue() {
        return Hashing.sha256().hashString(password.getValue(), StandardCharsets.UTF_8).toString();
    }
}
