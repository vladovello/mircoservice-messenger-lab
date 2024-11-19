package com.messenger.chat.application.command;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SendMessageInDialogueCommand extends SendMessageInChatCommand {
    private @NotNull UUID senderId;
    private @NotNull UUID recipientId;
    private @NotNull String messageText;
    private @NotNull List<CreateAttachmentDto> attachments;
}
