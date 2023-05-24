package com.messenger.chat.application.query;

import com.messenger.sharedlib.pagination.PageNumberPaginatedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatListQuery extends PageNumberPaginatedQuery {
    private UUID requesterId;
    private String chatName;

    public ChatListQuery(UUID requesterId, int pageNumber, int pageSize, String chatName) {
        super(pageNumber, pageSize);
        this.requesterId = requesterId;
        this.chatName = chatName;
    }
}
