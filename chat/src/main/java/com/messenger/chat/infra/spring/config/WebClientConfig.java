package com.messenger.chat.infra.spring.config;

import com.messenger.chat.infra.spring.config.prop.IntegrationProps;
import com.messenger.security.SecurityConst;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Getter
@Setter
@Slf4j
public class WebClientConfig {
    private final IntegrationProps props;

    public WebClientConfig(IntegrationProps props) {
        this.props = props;
    }

    @Bean(name = "friendsClient")
    public WebClient createFriendsWebClient() {
        final var friendsProps = props.getFriends();

        final var httpClient = HttpClient
                .create()
                .headers(entries -> entries.add(SecurityConst.HEADER_API_KEY, friendsProps.getApiKey()))
                .responseTimeout(Duration.ofMillis(friendsProps.getTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(friendsProps.getTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(friendsProps.getTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient
                .builder()
                .baseUrl(friendsProps.getBaseUrl() + friendsProps.getRootPath())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}