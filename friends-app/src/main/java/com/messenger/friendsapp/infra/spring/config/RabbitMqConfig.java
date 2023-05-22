package com.messenger.friendsapp.infra.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.messenger.friendsapp.infra.spring.config.props.RabbitMqProps;
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
    private static final String FRIENDSHIP_MODIFIED_EXCHANGE = "friendshipModifiedExchange";
    private static final String BLACKLIST_MODIFIED_EXCHANGE = "blacklistModifiedExchange";

    private final RabbitMqProps rabbitMqProps;

    public RabbitMqConfig(RabbitMqProps rabbitMqProps) {
        this.rabbitMqProps = rabbitMqProps;
    }

    @Bean
    Queue queue() {
        return new Queue(rabbitMqProps.getQueueName(), true);
    }

    @Bean
    FanoutExchange friendshipExchange() {
        return new FanoutExchange(FRIENDSHIP_MODIFIED_EXCHANGE, true, false);
    }

    @Bean
    FanoutExchange blacklistExchange() {
        return new FanoutExchange(BLACKLIST_MODIFIED_EXCHANGE, true, false);
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
    public AmqpTemplate friendshipModifiedTemplate(@NonNull ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(FRIENDSHIP_MODIFIED_EXCHANGE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public AmqpTemplate blacklistModifiedTemplate(@NonNull ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(BLACKLIST_MODIFIED_EXCHANGE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
