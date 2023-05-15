package com.messenger.chat.application.query;

import com.messenger.chat.application.ChatUser;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatInfoQuery implements ChatUser {
    private UUID requesterId;
    private UUID chatId;
}
