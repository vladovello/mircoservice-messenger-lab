package com.messenger.chat.application.query;

import lombok.Data;

@Data
public class MessageListQuery {
    private int pageNumber;
    private int pageSize;
    private String messageText;
}
