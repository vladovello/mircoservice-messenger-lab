package com.messenger.chat.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MessageListQuery {
    private UUID requesterId;
    private int pageNumber;
    private int pageSize;
    private String messageText;
}
