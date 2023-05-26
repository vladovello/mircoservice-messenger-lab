package com.messenger.chat.application.command;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageInDialogueCommand {
    private UUID senderId;
    private UUID recipientId;
    private String messageText;
    private List<CreateAttachmentDto> attachments;
}
