package com.messenger.authandprofile.infra.spring.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security")
@Getter
@Setter
@ToString
public class SecurityProps {
    private SecurityIntegrationsProps integrations;
}
