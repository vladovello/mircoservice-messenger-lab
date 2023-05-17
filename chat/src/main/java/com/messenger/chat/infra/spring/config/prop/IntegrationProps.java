package com.messenger.chat.infra.spring.config.prop;

import com.messenger.sharedlib.integration.props.ServiceIntegrationProps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.services")
@Data
public class IntegrationProps {
    ServiceIntegrationProps friends;
}
