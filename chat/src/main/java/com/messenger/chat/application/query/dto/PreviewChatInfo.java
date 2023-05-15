package com.messenger.chat.application.query.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PreviewChatInfo {
    private UUID chatId;
    private String chatName;
    private ChatMessagePreview chatMessagePreview;
}
