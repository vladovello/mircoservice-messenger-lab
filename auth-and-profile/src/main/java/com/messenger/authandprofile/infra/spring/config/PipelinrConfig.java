package com.messenger.authandprofile.infra.spring.config;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import lombok.NonNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelinrConfig {
    @SuppressWarnings("rawtypes")
    @Bean
    Pipeline pipeline(
            @NonNull ObjectProvider<Command.Handler> commandHandlers,
            @NonNull ObjectProvider<Notification.Handler> notificationHandlers,
            @NonNull ObjectProvider<Command.Middleware> middlewares
    ) {
        return new Pipelinr()
                .with(commandHandlers::stream)
                .with(notificationHandlers::stream)
                .with(middlewares::orderedStream);
    }
}
