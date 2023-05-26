package com.messenger.chat.application.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AttachmentDto {
    private UUID id;
    private String name;
    private AttachmentSizeDto size;
}
