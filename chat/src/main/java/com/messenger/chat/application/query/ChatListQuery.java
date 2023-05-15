package com.messenger.chat.application.query;

import lombok.Data;

@Data
public class ChatListQuery {
    private int pageNumber;
    private int pageSize;
    private String chatName;
}
