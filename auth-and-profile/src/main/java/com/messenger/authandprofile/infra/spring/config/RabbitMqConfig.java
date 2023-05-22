package com.messenger.authandprofile.infra.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {
    private static final String USER_MODIFIED_EXCHANGE = "userModifiedExchange";
    private static final String USER_LOGGED_EXCHANGE = "userLoggedExchange";

    @Bean
    FanoutExchange userModifiedExchange() {
        return new FanoutExchange(USER_LOGGED_EXCHANGE, true, false);
    }

    @Bean
    FanoutExchange userLoggedExchange() {
        return new FanoutExchange(USER_LOGGED_EXCHANGE, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate userModifiedTemplate(@NonNull ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setExchange(USER_MODIFIED_EXCHANGE);
        return template;
    }

    @Bean
    public RabbitTemplate userLoggedTemplate(@NonNull ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setExchange(USER_LOGGED_EXCHANGE);
        return template;
    }
}
