package com.messenger.friendsapp.infra.integration;

import com.messenger.friendsapp.application.dto.FullNameDto;
import com.messenger.friendsapp.application.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private static final int RETRY_TIMEOUT_DURATION_MILLIS = 3000;

    @Qualifier("profileClient")
    private final WebClient profileClient;

    public ProfileRepositoryImpl(WebClient profileClient) {
        this.profileClient = profileClient;
    }

    @Override
    public Optional<FullNameDto> getUsernameById(UUID userId) {
        return profileClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/profile/full_name")
                        .queryParam("userId", userId.toString())
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Optional<FullNameDto>>() {})
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(RETRY_TIMEOUT_DURATION_MILLIS)))
                .block();
    }
}
