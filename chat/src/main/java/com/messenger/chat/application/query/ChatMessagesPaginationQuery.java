package com.messenger.chat.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatMessagesPaginationQuery {
    private UUID requesterId;
    private UUID chatId;
    private int offset;
}
