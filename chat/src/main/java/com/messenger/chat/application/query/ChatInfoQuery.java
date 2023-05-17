package com.messenger.chat.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatInfoQuery {
    private UUID requesterId;
    private UUID chatId;
}
