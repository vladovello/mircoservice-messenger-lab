package com.messenger.chat.application.query.repository;

import com.messenger.chat.application.query.dto.PreviewChatInfo;

import java.util.List;

public interface MessageQueryRepository {
    List<PreviewChatInfo> getMessagesPaginated(int pageNumber, int pageSize, String messageText);
}
