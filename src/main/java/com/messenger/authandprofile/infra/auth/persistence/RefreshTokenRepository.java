package com.messenger.authandprofile.infra.auth.persistence;

import com.messenger.authandprofile.infra.auth.persistence.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    List<RefreshToken> findAllByUserIdAndInvalid(UUID userId, boolean isInvalid);

    @Query(value = "SELECT RefreshToken.token FROM RefreshToken WHERE RefreshToken.token = :id AND RefreshToken.isInvalid = :invalid LIMIT 1")
    boolean existsByIdAndInvalid(String id, boolean invalid);
}
