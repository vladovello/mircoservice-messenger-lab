package com.messenger.chat.application.query;

import com.messenger.sharedlib.pagination.PageNumberPaginatedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageListQuery extends PageNumberPaginatedQuery {
    private UUID requesterId;
    private String messageText;

    public MessageListQuery(UUID requesterId, int pageNumber, int pageSize, String messageText) {
        super(pageNumber, pageSize);
        this.requesterId = requesterId;
        this.messageText = messageText;
    }

    @Override
    public int getMaxPageSize() {
        return 50;
    }
}
