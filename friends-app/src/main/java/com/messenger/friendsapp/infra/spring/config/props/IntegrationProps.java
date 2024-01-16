package com.messenger.friendsapp.infra.spring.config.props;

import com.messenger.sharedlib.infra.props.ServiceIntegrationProps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.services")
@Data
public class IntegrationProps {
    ServiceIntegrationProps profile;
}
