package com.messenger.chat.domain.message.service.impl;

import com.messenger.chat.domain.message.Attachment;
import com.messenger.chat.domain.message.exception.AttachmentNotFoundInStorageException;
import com.messenger.chat.domain.message.model.CreateAttachmentModel;
import com.messenger.chat.domain.message.repository.FileStorageRepository;
import com.messenger.chat.domain.message.service.AttachmentDomainService;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AttachmentDomainServiceImpl implements AttachmentDomainService {
    private final FileStorageRepository fileStorageRepository;

    public AttachmentDomainServiceImpl(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public Result<List<Attachment>> createAttachments(@NonNull List<CreateAttachmentModel> createAttachmentDtoList, UUID messageId) {
        var fileIds = createAttachmentDtoList.stream().map(CreateAttachmentModel::getId).collect(Collectors.toList());

        var isAllExists = fileStorageRepository.isFilesExists(fileIds);

        if (!isAllExists) {
            return Result.failure(new AttachmentNotFoundInStorageException());
        }

        var attachments = new ArrayList<Attachment>();

        for (var attachmentDto : createAttachmentDtoList) {
            attachments.add(Attachment.createNew(
                    messageId,
                    attachmentDto.getId(),
                    attachmentDto.getFileName(),
                    attachmentDto.getSize()
            ));
        }

        return Result.success(attachments);
    }
}
