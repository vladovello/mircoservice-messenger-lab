package com.messenger.chat.presentation.chat.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ChangeChatDto {
    private UUID chatId;
    private String chatName;
    private UUID avatarId;
    private List<UUID> usersList;
}
