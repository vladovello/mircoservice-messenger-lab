package com.messenger.authandprofile.infra.auth.jwt.tokenfactory;

import com.messenger.authandprofile.application.auth.model.TokenPair;

import java.util.Map;

public interface JwtBearerTokenFactory {
    String generateAccessToken(Map<String, ?> claims);

    String generateRefreshToken(Map<String, ?> claims);

    TokenPair generateTokenPair(Map<String, ?> refreshTokenClaims, Map<String, ?> accessTokenClaims);
}
