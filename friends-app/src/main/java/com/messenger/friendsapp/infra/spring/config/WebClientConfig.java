package com.messenger.friendsapp.infra.spring.config;

import com.messenger.friendsapp.infra.spring.config.props.IntegrationProps;
import com.messenger.security.SecurityConst;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
public class WebClientConfig {
    private final IntegrationProps props;

    public WebClientConfig(IntegrationProps props) {
        this.props = props;
    }

    @Bean(name = "profileClient")
    public WebClient createFriendsWebClient() {
        final var profileProps = props.getProfile();

        final var httpClient = HttpClient
                .create()
                .headers(entries -> entries.add(SecurityConst.HEADER_API_KEY, profileProps.getApiKey()))
                .responseTimeout(Duration.ofMillis(profileProps.getTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(profileProps.getTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(profileProps.getTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient
                .builder()
                .baseUrl(profileProps.getBaseUrl() + profileProps.getRootPath())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
