package com.messenger.chat.application.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttachmentDto {
    private String id;
    private String fileName;
    private long size;
}
