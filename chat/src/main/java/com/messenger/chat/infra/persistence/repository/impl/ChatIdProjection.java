package com.messenger.chat.infra.persistence.repository.impl;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatIdProjection {
    private UUID chatId;
}
