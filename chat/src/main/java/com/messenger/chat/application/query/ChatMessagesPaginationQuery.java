package com.messenger.chat.application.query;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessagesPaginationQuery {
    private UUID requesterId;
    private UUID chatId;
    private int offset;
}
