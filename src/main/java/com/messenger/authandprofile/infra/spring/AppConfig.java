package com.messenger.authandprofile.infra.spring;

import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactoryImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class AppConfig {
    // TODO: 14.04.2023 add configs for access and refresh tokens
    @Bean
    public JwtBearerTokenFactory jwtBearerTokenFactory() {
        return new JwtBearerTokenFactoryImpl(accessTokenParams -> {
            accessTokenParams.setAsymmetricKey(Keys.keyPairFor(SignatureAlgorithm.HS512));
            accessTokenParams.setLifespan(LocalTime.of(0, 5, 0));
        }, refreshTokenParams -> {
            refreshTokenParams.setAsymmetricKey(Keys.keyPairFor(SignatureAlgorithm.HS512));
            refreshTokenParams.setLifespan(LocalTime.of(12, 0, 0));
        });
    }
}
