package com.messenger.chat.domain.message.service.impl;

import com.messenger.chat.domain.chat.PermissionManager;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.exception.NotEnoughPermissionsException;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.repository.MessageRepository;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

public class MessageDomainServiceImpl implements MessageDomainService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final MessageRepository messageRepository;

    public MessageDomainServiceImpl(
            ChatParticipantRepository chatParticipantRepository,
            MessageRepository messageRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Result<Unit> sendMessage(@NonNull Message message) {
        if (!chatParticipantRepository.isChatParticipantExists(message.getChatId(), message.getUserId())) {
            return Result.failure(new UserDoesNotExistsInChatException(message.getUserId(), message.getChatId()));
        }

        messageRepository.save(message);

        return Result.success();
    }

    @Override
    public Result<Unit> changeMessage(ChatParticipant chatParticipant, Message message) {
        if (!PermissionManager.canManageMessage(chatParticipant, message)) {
            return Result.failure(new NotEnoughPermissionsException(chatParticipant.getUser().getUserId()));
        }

        messageRepository.save(message);

        return Result.success();
    }

    @Override
    public Result<Unit> deleteMessage(ChatParticipant chatParticipant, Message message) {
        if (!PermissionManager.canManageMessage(chatParticipant, message)) {
            return Result.failure(new NotEnoughPermissionsException(chatParticipant.getUser().getUserId()));
        }

        messageRepository.delete(message);

        return Result.success();
    }
}
