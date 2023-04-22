package com.messenger.authandprofile.infra.spring.config;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import com.messenger.authandprofile.infra.auth.jwt.factory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.jwt.factory.JwtBearerTokenFactoryImpl;
import com.messenger.authandprofile.infra.auth.jwt.validator.JwtBearerTokenValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.TokenValidator;
import com.messenger.authandprofile.infra.spring.config.props.JwtTokenProps;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {
    private final JwtBearerTokenParameters accessTokenParams;
    private final JwtBearerTokenParameters refreshTokenParams;

    public JwtTokenConfig(JwtTokenProps jwtTokenProps) {
        accessTokenParams = new JwtBearerTokenParameters(parameters -> {
            parameters.setSymmetricKey(Keys.hmacShaKeyFor(jwtTokenProps.getSecretKey().getBytes()));
            parameters.setLifespan(jwtTokenProps.getAccessToken().getLifespan());
        });

        refreshTokenParams = new JwtBearerTokenParameters(parameters -> {
            parameters.setSymmetricKey(Keys.hmacShaKeyFor(jwtTokenProps.getSecretKey().getBytes()));
            parameters.setLifespan(jwtTokenProps.getRefreshToken().getLifespan());
        });
    }

    @Bean
    public JwtBearerTokenFactory jwtBearerTokenFactory() {
        return new JwtBearerTokenFactoryImpl(accessTokenParams, refreshTokenParams);
    }

    @Bean("accessTokenValidator")
    public TokenValidator jwtAccessTokenValidator() {
        return new JwtBearerTokenValidator(accessTokenParams);
    }

    @Bean("refreshTokenValidator")
    public TokenValidator jwtRefreshTokenValidator() {
        return new JwtBearerTokenValidator(refreshTokenParams);
    }
}
