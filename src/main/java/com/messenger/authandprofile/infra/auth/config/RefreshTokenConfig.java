package com.messenger.authandprofile.infra.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalTime;

public final class RefreshTokenConfig {
    public static final SignatureAlgorithm SIGNING_ALGORITHM = SignatureAlgorithm.ES512;
    public static final LocalTime LIFESPAN = LocalTime.of(12, 0, 0);

    private RefreshTokenConfig() {
    }
}
