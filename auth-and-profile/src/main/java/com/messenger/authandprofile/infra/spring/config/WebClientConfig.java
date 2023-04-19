package com.messenger.authandprofile.infra.spring.config;

import com.messenger.authandprofile.infra.domain.externalapi.props.ServiceIntegrationProps;
import com.messenger.authandprofile.infra.spring.security.SecurityConst;
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
    private final ServiceIntegrationProps props;

    public WebClientConfig(ServiceIntegrationProps props) {
        this.props = props;
    }

    @Bean(name = "friendsClient")
    public WebClient createFriendsWebClient() {
        final var httpClient = HttpClient
                .create()
                .baseUrl(props.getBaseUrl())
                .responseTimeout(Duration.ofMillis(props.getTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(props.getTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(props.getTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter((request, next) -> {
                    request.headers().add(SecurityConst.HEADER_API_KEY, props.getApiKey());
                    return next.exchange(request);
                })
                .build();
    }
}
