package com.messenger.authandprofile.infra.spring.config;

import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactoryImpl;
import com.messenger.authandprofile.infra.spring.config.props.JwtTokenProps;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {
    private final JwtTokenProps jwtTokenProps;

    public JwtTokenConfig(JwtTokenProps jwtTokenProps) {
        this.jwtTokenProps = jwtTokenProps;
    }

    @Bean
    public JwtBearerTokenFactory jwtBearerTokenFactory() {
        return new JwtBearerTokenFactoryImpl(accessTokenParams -> {
            accessTokenParams.setAsymmetricKey(Keys.keyPairFor(jwtTokenProps.getAccessToken()
                    .getSigningAlgorithm()));
            accessTokenParams.setLifespan(jwtTokenProps.getAccessToken().getLifespan());
        }, refreshTokenParams -> {
            refreshTokenParams.setAsymmetricKey(Keys.keyPairFor(jwtTokenProps.getRefreshToken()
                    .getSigningAlgorithm()));
            refreshTokenParams.setLifespan(jwtTokenProps.getRefreshToken().getLifespan());
        });
    }
}
