package com.messenger.authandprofile.infra.auth.persistence;

import com.messenger.authandprofile.infra.auth.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    List<RefreshTokenEntity> findAllByUserIdAndInvalid(UUID userId, boolean isInvalid);

    @Query(value = "SELECT RefreshTokenEntity.token FROM RefreshTokenEntity WHERE RefreshTokenEntity.token = :id AND RefreshTokenEntity.isInvalid = :invalid LIMIT 1")
    boolean existsByIdAndInvalid(String id, boolean invalid);
}
