package com.messenger.notification.messagebroker.config.props;

import lombok.Data;
import org.springframework.amqp.core.Binding;

@Data
public class BindingProp {
    private String destination;
    private Binding.DestinationType destinationType;
    private String exchange;
    private String routingKey;
}
