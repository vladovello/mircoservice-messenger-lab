package com.messenger.chat.application.query.dto;

import com.messenger.chat.domain.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MessageDto {
    private UUID messageId;
    private LocalDateTime sendingDate;
    private String messageText;
    private String senderName;
    private UUID avatarId;
    private List<AttachmentDto> attachments;

    public static MessageDto toMessageDto(@NonNull Message message) {
        return new MessageDto(
                message.getId(),
                message.getCreationDate(),
                message.getMessageText().getText(),
                message.getChatUser().getFullName().getValue(),
                message.getChatUser().getAvatarId(),
                message.getAttachments().stream().map(attachment -> new AttachmentDto(
                        attachment.getId(),
                        attachment.getFileName(),
                        AttachmentSizeDto.fromBytes(attachment.getFileSize())
                )).collect(Collectors.toList())
        );
    }
}
