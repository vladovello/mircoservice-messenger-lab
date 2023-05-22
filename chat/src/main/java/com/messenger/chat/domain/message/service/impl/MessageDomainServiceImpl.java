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
    private final MessageRepository messageRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    public MessageDomainServiceImpl(
            MessageRepository messageRepository,
            ChatParticipantRepository chatParticipantRepository
    ) {
        this.messageRepository = messageRepository;
        this.chatParticipantRepository = chatParticipantRepository;
    }

    @Override
    public Result<Unit> sendMessage(@NonNull Message message) {
        if (!chatParticipantRepository.isUserBelongsToChat(message.getChatId(), message.getSenderUserId())) {
            return Result.failure(new UserDoesNotExistsInChatException(message.getSenderUserId(), message.getChatId()));
        }

        messageRepository.save(message);

        return Result.success();
    }

    @Override
    public Result<Unit> changeMessage(ChatParticipant chatParticipant, Message message) {
        if (!PermissionManager.canManageMessage(chatParticipant, message)) {
            return Result.failure(new NotEnoughPermissionsException(chatParticipant.getChatUser().getUserId()));
        }

        messageRepository.save(message);

        return Result.success();
    }

    @Override
    public Result<Unit> deleteMessage(ChatParticipant chatParticipant, Message message) {
        if (!PermissionManager.canManageMessage(chatParticipant, message)) {
            return Result.failure(new NotEnoughPermissionsException(chatParticipant.getChatUser().getUserId()));
        }

        messageRepository.delete(message);

        return Result.success();
    }
}
