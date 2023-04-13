package com.messenger.authandprofile.infra.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtConfig {
    public static final SignatureAlgorithm SIGNING_ALGORITHM = SignatureAlgorithm.HS512;
}
