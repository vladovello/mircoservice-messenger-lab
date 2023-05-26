package com.messenger.chat.presentation.message.dto;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentPresDto {
    private String id;
    private String fileName;
    private long size;

    public CreateAttachmentDto toAttachmentDto() {
        return new CreateAttachmentDto(id, fileName, size);
    }
}
