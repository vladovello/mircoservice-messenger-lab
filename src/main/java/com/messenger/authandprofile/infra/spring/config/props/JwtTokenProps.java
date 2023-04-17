package com.messenger.authandprofile.infra.spring.config.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security.jwt")
@Getter
@Setter
@ToString
public class JwtTokenProps {
    private AccessTokenProps accessToken;
    private RefreshTokenProps refreshToken;
    private String secretKey;
}
