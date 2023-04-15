package com.messenger.authandprofile.infra.spring.config;

import com.messenger.authandprofile.infra.auth.config.AccessTokenConfig;
import com.messenger.authandprofile.infra.auth.config.RefreshTokenConfig;
import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactoryImpl;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {
    @Bean
    public JwtBearerTokenFactory jwtBearerTokenFactory() {
        return new JwtBearerTokenFactoryImpl(accessTokenParams -> {
            accessTokenParams.setAsymmetricKey(Keys.keyPairFor(AccessTokenConfig.SIGNING_ALGORITHM));
            accessTokenParams.setLifespan(AccessTokenConfig.LIFESPAN);
        }, refreshTokenParams -> {
            refreshTokenParams.setAsymmetricKey(Keys.keyPairFor(RefreshTokenConfig.SIGNING_ALGORITHM));
            refreshTokenParams.setLifespan(RefreshTokenConfig.LIFESPAN);
        });
    }
}
