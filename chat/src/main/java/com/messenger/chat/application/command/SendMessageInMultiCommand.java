package com.messenger.chat.application.command;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SendMessageInMultiCommand extends SendMessageInChatCommand {
    private UUID senderId;
    private UUID chatId;
    private String messageText;
    private List<CreateAttachmentDto> attachments;
}
