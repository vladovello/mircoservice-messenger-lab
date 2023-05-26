package com.messenger.chat.domain.message.service;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import com.messenger.chat.domain.message.Attachment;
import com.messenger.sharedlib.util.Result;

import java.util.List;
import java.util.UUID;

public interface AttachmentDomainService {
    /**
     * Creates attachment of {@code List<CreateAttachmentDto>}
     * @param createAttachmentDtoList attachment representations
     * @param messageId id of the message to stick attachments to
     * @return {@code Result<List<Attachment>>} describing whether the result of the function execution was successful.
     */
    Result<List<Attachment>> createAttachments(
            List<CreateAttachmentDto> createAttachmentDtoList,
            UUID messageId
    );
}
