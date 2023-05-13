package com.messenger.chat.domain.chatparticipant.service.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.PermissionManager;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.businessrule.ChatIsNotFullRule;
import com.messenger.chat.domain.chatparticipant.exception.IllegalChatTypeException;
import com.messenger.chat.domain.chatparticipant.exception.NotEnoughPermissionsException;
import com.messenger.chat.domain.chatparticipant.exception.UserAlreadyInChatException;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.chatparticipant.service.ChatParticipantDomainService;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.DomainService;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

import java.util.UUID;

public class ChatParticipantDomainServiceImpl extends DomainService implements ChatParticipantDomainService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRepository chatRepository;

    public ChatParticipantDomainServiceImpl(
            ChatParticipantRepository chatParticipantRepository,
            ChatRepository chatRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public Result<ChatParticipant> addUserToMultiChat(
            @NonNull ChatParticipant invitingChatParticipant,
            UUID inviteeUserId
    ) {
        // 1. Check if chat is Dialogue
        var chatId = invitingChatParticipant.getChatId();
        var optionalChat = chatRepository.getChat(chatId);

        if (optionalChat.isEmpty()) {
            return Result.failure(new ChatNotFoundException(chatId));
        }

        var chat = optionalChat.get();

        if (chat.getChatType() == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        // 2. Common checks for all chats
        var result = performCommonChatInvitingChecks(chat, inviteeUserId);
        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        return Result.success(ChatParticipant.createNew(
                inviteeUserId,
                chatId,
                chat.getEveryoneRole()
        ));
    }

    @Override
    public Result<ChatParticipant> addUserToDialogue(UUID chatId, UUID userId) {
        // 1. Check if chat is Multi
        var optionalChat = chatRepository.getChat(chatId);

        if (optionalChat.isEmpty()) {
            return Result.failure(new ChatNotFoundException(chatId));
        }

        var chat = optionalChat.get();

        if (chat.getChatType() == ChatType.MULTI) {
            return Result.failure(IllegalChatTypeException.ofMulti());
        }

        var result = performCommonChatInvitingChecks(chat, userId);
        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        return Result.success(ChatParticipant.createNew(
                userId,
                chatId,
                chat.getEveryoneRole()
        ));
    }

    @Override
    public Result<Unit> kickFromChat(
            @NonNull ChatParticipant deletingChatParticipant,
            ChatParticipant beingDeletedChatParticipant
    ) {
        // 1. Check if participants is in the same chat
        if (deletingChatParticipant.isNotInSameChat(beingDeletedChatParticipant)) {
            return Result.failure(new UserDoesNotExistsInChatException(
                    beingDeletedChatParticipant.getChatParticipantId(),
                    deletingChatParticipant.getChatId()
            ));
        }

        // 2. Check if chat type is Dialogue
        if (chatRepository.getChatType(deletingChatParticipant.getChatId()) == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        // 3. Check if deleting user has enough permissions
        if (!PermissionManager.canKickMember(deletingChatParticipant, beingDeletedChatParticipant)) {
            return Result.failure(new NotEnoughPermissionsException(deletingChatParticipant.getUser().getUserId()));
        }

        chatParticipantRepository.delete(beingDeletedChatParticipant);

        return Result.success();
    }

    @Override
    public Result<Unit> leaveFromChat(@NonNull ChatParticipant chatParticipant) {
        // Check if chat type is Dialogue
        if (chatRepository.getChatType(chatParticipant.getChatId()) == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        chatParticipantRepository.delete(chatParticipant);

        return Result.success();
    }

    private @NonNull Result<Unit> performCommonChatInvitingChecks(@NonNull Chat chat, UUID inviteeId) {
        var chatId = chat.getId();

        // Check if invitee user is already in chat
        if (chatParticipantRepository.isChatParticipantExists(chatId, inviteeId)) {
            return Result.failure(new UserAlreadyInChatException(inviteeId, chatId));
        }

        // Check if chat is full
        try {
            checkRule(new ChatIsNotFullRule(
                    chat.getChatType(),
                    chatParticipantRepository.getParticipantsCount(chatId)
            ));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        return Result.success();
    }
}
