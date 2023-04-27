package com.messenger.security;

import com.messenger.security.integration.IntegrationFilter;
import com.messenger.security.jwt.JwtFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
class AuthSecurityConfig {
    private final SecurityProps securityProps;

    /**
     * Настройка для JWT
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http) {
        http = http.addFilterBefore(
                        new JwtFilter(securityProps.getJwtAuth().getValidationParams()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .requestMatcher(filterPredicate(
                        securityProps.getJwtAuth().getRootPath(),
                        securityProps.getJwtAuth().getPermitAll()
                ));

        return buildWith(http);
    }

    /**
     * Настройка для интеграции между сервисами
     */
    @Bean
    public SecurityFilterChain filterChainIntegration(HttpSecurity http) {
        http = http.requestMatcher(filterPredicate(securityProps.getIntegrations().getRootPath()))
                .addFilterBefore(
                        new IntegrationFilter(securityProps.getIntegrations().getApiKey()),
                        UsernamePasswordAuthenticationFilter.class
                );

        return buildWith(http);
    }

    @SneakyThrows
    private SecurityFilterChain buildWith(@NonNull HttpSecurity http) {
        return http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    private @NonNull RequestMatcher filterPredicate(String rootPath, String... ignore) {
        // Условие, что
        // 1. Путь сервлета задан
        // 2. Путь сервлета соответствует требуемому паттерну (начинается с rootPath)
        // 3. Путь сервлета не соответствует паттерну игнорируемых путей
        return rq -> Objects.nonNull(rq.getServletPath()) && rq.getServletPath().startsWith(rootPath) && Arrays.stream(
                ignore).noneMatch(item -> rq.getServletPath().startsWith(item));
    }
}

