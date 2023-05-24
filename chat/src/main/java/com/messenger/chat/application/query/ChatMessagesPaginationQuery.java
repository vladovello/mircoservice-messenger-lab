package com.messenger.chat.application.query;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessagesPaginationQuery {
    private UUID requesterId;
    private UUID chatId;
    private int offset;

    public ChatMessagesPaginationQuery(UUID requesterId, UUID chatId, int offset) {
        this.requesterId = requesterId;
        this.chatId = chatId;
        this.setOffset(offset);
    }

    public void setOffset(int offset) {
        this.offset = Math.max(offset, 0);
    }
}
