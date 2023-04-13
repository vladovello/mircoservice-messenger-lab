package com.messenger.authandprofile.infra.auth.persistence.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(
        indexes = @Index(columnList = "userId")
)
@Getter
public class RefreshToken {
    @Id
    @Column(nullable = false)
    @Setter
    private String token;

    @Column(nullable = false)
    @Setter
    private UUID userId;

    @Column(nullable = false)
    private boolean isInvalid;

    public RefreshToken() {
        // For JPA
    }

    public RefreshToken(@NonNull String token, @NonNull UUID userId) {
        this.token = token;
        this.userId = userId;
        this.isInvalid = false;
    }

    public void setInvalid() {
        isInvalid = true;
    }
}
