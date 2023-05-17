package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.user.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;

@Repository
public class BlacklistRepositoryImpl implements BlacklistRepository {
    @Qualifier("friendsClient")
    private final WebClient friendsService;
    private static final int RETRY_TIMEOUT_DURATION_MILLIS = 3000;

    public BlacklistRepositoryImpl(WebClient friendsService) {
        this.friendsService = friendsService;
    }

    @Override
    public boolean isUserIsBlacklistedByOther(UUID userId, UUID otherUserId) {
        var response = friendsService
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/friends/blacklist/check")
                        .queryParam("userId", userId.toString())
                        .queryParam("otherId", otherUserId.toString())
                        .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(RETRY_TIMEOUT_DURATION_MILLIS)))
                .block();

        return Boolean.TRUE.equals(response);
    }
}
