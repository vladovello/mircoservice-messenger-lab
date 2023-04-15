package com.messenger.authandprofile.infra.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalTime;

public final class AccessTokenConfig {
    public static final SignatureAlgorithm SIGNING_ALGORITHM = SignatureAlgorithm.ES512;
    public static final LocalTime LIFESPAN = LocalTime.of(0, 5, 0);

    private AccessTokenConfig() {
    }
}
