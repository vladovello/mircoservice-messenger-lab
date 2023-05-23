package com.messenger.chat.presentation.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateMessageMultiDto {
    private UUID chatId;
    private String messageText;
}
