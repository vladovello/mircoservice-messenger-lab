package com.messenger.security;

import com.messenger.security.integration.SecurityIntegrationsProps;
import com.messenger.security.jwt.SecurityJwtProps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("app.security")
@Configuration
@Getter
@Setter
@ToString
public class SecurityProps {
    private SecurityIntegrationsProps integrations;
    private SecurityJwtProps jwtAuth;
}
