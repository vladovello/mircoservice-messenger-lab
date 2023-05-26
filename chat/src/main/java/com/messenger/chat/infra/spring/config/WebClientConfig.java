package com.messenger.chat.infra.spring.config;

import com.messenger.chat.infra.spring.config.prop.IntegrationProps;
import com.messenger.security.SecurityConst;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.NonNull;
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

        final var httpClient = defaultHttpClient(friendsProps.getApiKey(), friendsProps.getTimeout());

        return WebClient
                .builder()
                .baseUrl(friendsProps.getBaseUrl() + friendsProps.getRootPath())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean(name = "fileStorageClient")
    public WebClient createFileStorageWebClient() {
        final var fileStorageProps = props.getFileStorage();

        final var httpClient = defaultHttpClient(fileStorageProps.getApiKey(), fileStorageProps.getTimeout());

        return WebClient
                .builder()
                .baseUrl(fileStorageProps.getBaseUrl() + fileStorageProps.getRootPath())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private static @NonNull HttpClient defaultHttpClient(String apiKey, int timeout) {
        return HttpClient
                .create()
                .headers(entries -> entries.add(SecurityConst.HEADER_API_KEY, apiKey))
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));
    }
}