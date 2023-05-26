package com.messenger.notification.messagebroker.config.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.rabbitmq")
@Getter
@Setter
@ToString
public class RabbitMqProps {
//    private List<QueueProp> queues;
//    private List<ExchangeProp> exchanges;
//    private List<BindingProp> bindings;
    private String queueName;
}
