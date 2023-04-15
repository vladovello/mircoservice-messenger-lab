package com.messenger.authandprofile.infra.spring.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "services")
@Configuration
@Getter
@Setter
public class WebClientConfig {
    @Value(value = "friends.url") private String friendsUrl;

    @Value(value = "friends.timeout") private Integer timeout;

    @Bean(name = "friendsClient")
    public WebClient createFriendsWebClient() {
        final var httpClient = HttpClient
                .create()
                .baseUrl(friendsUrl)
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}
