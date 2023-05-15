package com.messenger.chat.application.query;

import com.messenger.chat.application.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatMessagesPaginationQuery implements ChatUser {
    private UUID requesterId;
    private UUID chatId;
    private int offset;
}
