package com.messenger.chat.application.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PreviewChatInfo {
    private UUID chatId;
    private String chatName;
    private ChatMessagePreview chatMessagePreview;
}
