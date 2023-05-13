package com.messenger.chat.application.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatCommandDto {
    private UUID id;
}
