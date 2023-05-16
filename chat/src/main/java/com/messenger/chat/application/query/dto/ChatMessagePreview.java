package com.messenger.chat.application.query.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatMessagePreview {
    @NonNull private String messageText;
    @NonNull private LocalDateTime creationDate;
    @NonNull private UUID senderId;
    private boolean hasAttachment;
    private String attachmentName;
}
