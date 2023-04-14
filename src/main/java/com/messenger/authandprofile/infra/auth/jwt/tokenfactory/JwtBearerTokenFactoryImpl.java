package com.messenger.authandprofile.infra.auth.jwt.tokenfactory;

import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import io.jsonwebtoken.Jwts;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.Map;
import java.util.function.Consumer;

public final class JwtBearerTokenFactoryImpl implements JwtBearerTokenFactory {
    private JwtBearerTokenParameters accessJwtBearerTokenParameters;
    private JwtBearerTokenParameters refreshJwtBearerTokenParameters;

    public JwtBearerTokenFactoryImpl(@NonNull Consumer<JwtBearerTokenParameters> accessTokenOptions,
            @NonNull Consumer<JwtBearerTokenParameters> refreshTokenOptions) {
        accessTokenOptions.accept(new JwtBearerTokenParameters());
        refreshTokenOptions.accept(new JwtBearerTokenParameters());
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
