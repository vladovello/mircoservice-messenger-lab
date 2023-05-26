package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.message.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Repository
public class FileStorageRepositoryImpl implements FileStorageRepository {
    private final WebClient fileStorageService;
    private static final int RETRY_TIMEOUT_DURATION_MILLIS = 3000;

    public FileStorageRepositoryImpl(@Qualifier("fileStorageClient") WebClient fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public boolean isFilesExists(List<String> ids) {
        var response = fileStorageService
                .get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("file", "exists")
                        .queryParam("ids", ids)
                        .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(RETRY_TIMEOUT_DURATION_MILLIS)))
                .block();

        return Boolean.TRUE.equals(response);
    }
}
