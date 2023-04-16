package com.messenger.authandprofile.infra.domain.externalapi.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.services.friends")
@Getter
@Setter
@ToString
public class ServiceIntegrationProps {
    private String baseUrl;
    private int timeout;
    private String apiKey;
}
