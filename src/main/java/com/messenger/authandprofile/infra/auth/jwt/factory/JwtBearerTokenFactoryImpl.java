package com.messenger.authandprofile.infra.auth.jwt.factory;

import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import io.jsonwebtoken.Jwts;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.Map;

public final class JwtBearerTokenFactoryImpl implements JwtBearerTokenFactory {
    private final JwtBearerTokenParameters accessJwtBearerTokenParameters;
    private final JwtBearerTokenParameters refreshJwtBearerTokenParameters;

    public JwtBearerTokenFactoryImpl(
            @NonNull JwtBearerTokenParameters accessTokenOptions,
            @NonNull JwtBearerTokenParameters refreshTokenOptions
    ) {
        accessJwtBearerTokenParameters = accessTokenOptions;
        refreshJwtBearerTokenParameters = refreshTokenOptions;
    }

    @Override
    public String generateAccessToken(Map<String, ?> claims) {
        return generateToken(claims, accessJwtBearerTokenParameters);
    }

    @Override
    public String generateRefreshToken(Map<String, ?> claims) {
        return generateToken(claims, refreshJwtBearerTokenParameters);
    }

    @Contract("_, _ -> new")
    @Override
    public @NonNull TokenPair generateTokenPair(Map<String, ?> refreshTokenClaims, Map<String, ?> accessTokenClaims) {
        return new TokenPair(generateAccessToken(accessTokenClaims), generateRefreshToken(refreshTokenClaims));
    }

    private String generateToken(@NonNull Map<String, ?> claims, @NonNull JwtBearerTokenParameters options) {
        return Jwts.builder()
                .setIssuer(options.getIssuer())
                .setExpiration(options.getExpirationDate())
                .setClaims(claims)
                .signWith(options.getSigningKey())
                .compact();
    }
}
