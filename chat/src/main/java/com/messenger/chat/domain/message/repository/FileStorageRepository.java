package com.messenger.chat.domain.message.repository;

import java.util.List;

public interface FileStorageRepository {
    boolean isFilesExists(List<String> ids);
}
