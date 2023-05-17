package com.messenger.chat.application.query.repository;

import com.messenger.chat.application.query.dto.PreviewChatInfo;

import java.util.List;
import java.util.UUID;

public interface ChatQueryRepository {
    List<PreviewChatInfo> getUserChatsPaginated(UUID requesterId, int pageNumber, int pageSize, String chatName);
    List<PreviewChatInfo> getUserMessagesPaginated(UUID requesterId, int pageNumber, int pageSize, String messageText);
}
