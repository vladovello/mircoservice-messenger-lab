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
public class RefreshTokenEntity {
    @Id
    @Column(nullable = false)
    @Getter
    @Setter
    private String token;

    @Column(nullable = false)
    @Getter
    @Setter
    private UUID userId;

    @Column(nullable = false)
    private boolean invalid;

    public RefreshTokenEntity() {
        // For JPA
    }

    public RefreshTokenEntity(@NonNull String token, @NonNull UUID userId) {
        this.token = token;
        this.userId = userId;
        this.invalid = false;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid() {
        invalid = true;
    }
}
