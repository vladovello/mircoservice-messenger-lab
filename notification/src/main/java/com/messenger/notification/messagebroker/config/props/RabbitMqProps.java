package com.messenger.notification.messagebroker.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.rabbitmq")
@Data
public class RabbitMqProps {
    private List<QueueProp> queues;
    private List<ExchangeProp> exchanges;
    private List<BindingProp> bindings;
}
