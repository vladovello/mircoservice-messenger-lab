package com.messenger.notification.messagebroker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.messenger.notification.messagebroker.config.props.RabbitMqProps;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {
    private final RabbitMqProps rabbitMqProps;
    private final String dlq;
    private final String dlx;

    public RabbitMqConfig(RabbitMqProps rabbitMqProps) {
        this.rabbitMqProps = rabbitMqProps;
        this.dlq = rabbitMqProps.getQueueName() + ".dlq";
        this.dlx = rabbitMqProps.getQueueName() + ".dlx";
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(rabbitMqProps.getQueueName()).deadLetterExchange(dlx).build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(dlx);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    Binding blacklistModifiedBinding(@NonNull Queue queue) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, "blacklistModifiedExchange", "", null);
    }

    @Bean
    Binding friendshipModifiedBinding(@NonNull Queue queue) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, "friendshipModifiedExchange", "", null);
    }

    @Bean
    Binding messageCreatedBinding(@NonNull Queue queue) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, "messageCreatedExchange", "", null);
    }

    @Bean
    Binding userLoggedBinding(@NonNull Queue queue) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, "userLoggedExchange", "", null);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

//    @Bean
//    List<Queue> queues() {
//        var queues = new ArrayList<Queue>();
//
//        for (var queueProp : rabbitMq.getQueues()) {
//            queues.add(new Queue(
//                    queueProp.getName(),
//                    queueProp.isDurable(),
//                    queueProp.isExclusive(),
//                    queueProp.isAutoDelete(),
//                    queueProp.getArguments()
//            ));
//        }
//
//        return queues;
//    }
//
//    @Bean
//    List<Exchange> exchanges() {
//        var exchanges = new ArrayList<Exchange>();
//
//        for (var exchangeProp : rabbitMq.getExchanges()) {
//            switch (exchangeProp.getType().toLowerCase()) {
//                case "fanout":
//                    exchanges.add(
//                            ExchangeBuilder
//                                    .fanoutExchange(exchangeProp.getName())
//                                    .durable(exchangeProp.isDurable()).build()
//                    );
//                    break;
//                default:
//                    log.info("No such type {}", exchangeProp.getType());
//            }
//        }
//
//        return exchanges;
//    }
//
//    @Bean
//    List<Binding> bindings() {
//        var bindings = new ArrayList<Binding>();
//
//        for (var bindingProp : rabbitMq.getBindings()) {
//            bindings.add(new Binding(
//                    bindingProp.getDestination(),
//                    bindingProp.getDestinationType(),
//                    bindingProp.getExchange(),
//                    bindingProp.getRoutingKey(),
//                    null
//            ));
//        }
//
//        return bindings;
//    }
}
