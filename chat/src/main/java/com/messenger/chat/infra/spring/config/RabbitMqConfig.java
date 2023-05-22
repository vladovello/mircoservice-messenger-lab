package com.messenger.chat.infra.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.messenger.chat.infra.spring.config.prop.RabbitMqProps;
import lombok.NonNull;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private static final String MESSAGE_CREATED_EXCHANGE = "messageCreatedExchange";

    private final RabbitMqProps rabbitMqProps;

    public RabbitMqConfig(RabbitMqProps rabbitMqProps) {
        this.rabbitMqProps = rabbitMqProps;
    }

    @Bean
    Queue queue() {
        return new Queue(rabbitMqProps.getQueueName(), true);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(MESSAGE_CREATED_EXCHANGE, true, false);
    }

    @Bean
    Binding userModifiedBinding(@NonNull Queue queue) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, "userModifiedExchange", "", null);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate messageCreatedTemplate(@NonNull ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(MESSAGE_CREATED_EXCHANGE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
