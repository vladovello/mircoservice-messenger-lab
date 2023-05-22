package com.messenger.chat.infra.spring.config.prop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.rabbitmq")
@Getter
@Setter
@ToString
public class RabbitMqProps {
    private String queueName;
}
