package com.messenger.chat.presentation.message.dto;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateMessageDialogueDto {
    private UUID recipientId;
    private String messageText;
    private List<CreateAttachmentDto> attachments;
}
