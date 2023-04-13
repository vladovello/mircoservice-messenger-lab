package com.messenger.authandprofile.infra.auth.persistence;

import com.messenger.authandprofile.infra.auth.persistence.entity.InvalidAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidAccessTokenRepository extends JpaRepository<InvalidAccessToken, String> {
}
