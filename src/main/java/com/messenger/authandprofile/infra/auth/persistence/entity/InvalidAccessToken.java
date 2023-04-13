package com.messenger.authandprofile.infra.auth.persistence.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class InvalidAccessToken {
    @Id
    @Column(name = "token", nullable = false)
    @NonNull
    private String token;

    public InvalidAccessToken() {
        // For JPA
    }

    public InvalidAccessToken(@NonNull String token) {
        this.token = token;
    }
}
