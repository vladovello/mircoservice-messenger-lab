package com.messenger.chat.application.query.repository;

import com.messenger.chat.application.query.dto.PreviewChatInfo;

import java.util.List;

public interface ChatQueryRepository {
    List<PreviewChatInfo> getChatsPaginated(int pageNumber, int pageSize, String chatName);
}
