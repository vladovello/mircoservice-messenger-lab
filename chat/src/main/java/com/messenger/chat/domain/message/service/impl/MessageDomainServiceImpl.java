package com.messenger.chat.domain.message.service.impl;

import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.model.CreateAttachmentModel;
import com.messenger.chat.domain.message.repository.MessageRepository;
import com.messenger.chat.domain.message.service.AttachmentDomainService;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

import java.util.List;

public class MessageDomainServiceImpl implements MessageDomainService {
    private final MessageRepository messageRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final AttachmentDomainService attachmentDomainService;

    public MessageDomainServiceImpl(
            MessageRepository messageRepository,
            ChatParticipantRepository chatParticipantRepository, AttachmentDomainService attachmentDomainService
    ) {
        this.messageRepository = messageRepository;
        this.chatParticipantRepository = chatParticipantRepository;
        this.attachmentDomainService = attachmentDomainService;
    }

    @Override
    public Result<Unit> saveMessage(@NonNull Message message) {
        if (!chatParticipantRepository.isUserBelongsToChat(message.getChatId(), message.getSenderUserId())) {
            return Result.failure(new UserDoesNotExistsInChatException(message.getSenderUserId(), message.getChatId()));
        }

        messageRepository.save(message);

        return Result.success();
    }

    @Override
    public Result<Unit> assignAttachments(Message message, List<CreateAttachmentModel> attachmentModels) {
        var attachmentsResult = attachmentDomainService.createAttachments(
                attachmentModels,
                message.getId()
        );

        if (attachmentsResult.isFailure()) {
            return Result.failure(attachmentsResult.exceptionOrNull());
        }

        var attachments = attachmentsResult.getOrNull();

        message.assignAttachments(attachments);

        return saveMessage(message);
    }
}
