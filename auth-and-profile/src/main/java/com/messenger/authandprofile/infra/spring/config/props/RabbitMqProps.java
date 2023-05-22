package com.messenger.authandprofile.infra.spring.config.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.rabbitmq")
@Getter
@Setter
@ToString
public class RabbitMqProps {
    private String host;
    private String username;
    private String password;
}
