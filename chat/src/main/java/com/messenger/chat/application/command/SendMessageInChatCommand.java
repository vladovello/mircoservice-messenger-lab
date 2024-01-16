package com.messenger.chat.application.command;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import com.messenger.chat.domain.message.model.CreateAttachmentModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SendMessageInChatCommand {
    protected UUID senderId;
    protected UUID chatId;
    protected String messageText;
    protected List<CreateAttachmentDto> attachments;

    public List<CreateAttachmentModel> attachmentsToDomain() {
        return getAttachments()
                .stream()
                .map((CreateAttachmentDto::toDomain))
                .collect(Collectors.toList());
    }
}
