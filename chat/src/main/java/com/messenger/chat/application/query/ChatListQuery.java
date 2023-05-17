package com.messenger.chat.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListQuery {
    private UUID requesterId;
    private int pageNumber;
    private int pageSize;
    private String chatName;
}
