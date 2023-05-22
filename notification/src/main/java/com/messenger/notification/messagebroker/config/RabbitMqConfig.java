package com.messenger.notification.messagebroker.config;

import com.messenger.notification.messagebroker.config.props.RabbitMqProps;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMqConfig {
    private final RabbitMqProps rabbitMq;

    public RabbitMqConfig(RabbitMqProps rabbitMq) {
        this.rabbitMq = rabbitMq;
    }

    @Bean
    List<Queue> queues() {
        var queues = new ArrayList<Queue>();

        for (var queueProp : rabbitMq.getQueues()) {
            queues.add(new Queue(
                    queueProp.getName(),
                    queueProp.isDurable(),
                    queueProp.isExclusive(),
                    queueProp.isAutoDelete()
            ));
        }

        return queues;
    }

    @Bean
    List<Binding> bindings() {
        var bindings = new ArrayList<Binding>();

        for (var bindingProp : rabbitMq.getBindings()) {
            bindings.add(new Binding(
                    bindingProp.getDestination(),
                    bindingProp.getDestinationType(),
                    bindingProp.getExchange(),
                    bindingProp.getRoutingKey(),
                    null
            ));
        }

        return bindings;
    }
}
