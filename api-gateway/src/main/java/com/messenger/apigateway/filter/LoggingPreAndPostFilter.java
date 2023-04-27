package com.messenger.apigateway.filter;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingPreAndPostFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        log.info(String.format("Request on path '%s' started", exchange.getRequest().getPath()));
        return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info(String.format(
                "Request on path '%s' ended",
                exchange.getRequest().getPath()
        ))));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
