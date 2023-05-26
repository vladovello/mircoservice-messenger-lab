package com.messenger.chat.application.command;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SendMessageInMultiCommand {
    private UUID senderId;
    private UUID chatId;
    private String messageText;
    private List<CreateAttachmentDto> attachments;
}
