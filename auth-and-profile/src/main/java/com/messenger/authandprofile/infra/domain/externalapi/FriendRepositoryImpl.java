package com.messenger.authandprofile.infra.domain.externalapi;

import com.messenger.authandprofile.domain.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;

@Service
public class FriendRepositoryImpl implements FriendRepository {
    @Qualifier("friendsClient")
    private final WebClient friendsService;
    private static final int RETRY_TIMEOUT_DURATION_MILLIS = 3000;

    public FriendRepositoryImpl(WebClient friendsService) {
        this.friendsService = friendsService;
    }

    @Override
    public boolean isUserInOthersBlacklist(UUID userId, UUID otherId) {
        var response = friendsService
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/friends/blacklist/check")
                        .queryParam("userId", userId.toString())
                        .queryParam("otherId", otherId.toString())
                        .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(RETRY_TIMEOUT_DURATION_MILLIS)))
                .block();

        return Boolean.TRUE.equals(response);
    }
}
