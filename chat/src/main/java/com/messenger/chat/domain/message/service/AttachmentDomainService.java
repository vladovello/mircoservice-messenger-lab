package com.messenger.chat.domain.message.service;

import com.messenger.chat.application.command.dto.CreateAttachmentDto;
import com.messenger.chat.domain.message.Attachment;
import com.messenger.sharedlib.util.Result;

import java.util.List;
import java.util.UUID;

public interface AttachmentDomainService {
    Result<List<Attachment>> createAttachments(
            List<CreateAttachmentDto> createAttachmentDtoList,
            UUID messageId
    );
}
