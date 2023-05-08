package com.messenger.chat.domain.chatparticipant.service.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.ChatRole;
import com.messenger.chat.domain.chat.Permission;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.businessrule.ChatIsNotFullRule;
import com.messenger.chat.domain.chatparticipant.businessrule.HasRequiredPermissionsRule;
import com.messenger.chat.domain.chatparticipant.exception.IllegalChatTypeException;
import com.messenger.chat.domain.chatparticipant.exception.UserAlreadyInChatException;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.chatparticipant.service.ChatParticipantDomainService;
import com.messenger.chat.domain.user.UserRepository;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.DomainService;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ChatParticipantDomainServiceImpl extends DomainService implements ChatParticipantDomainService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatParticipantDomainServiceImpl(
            ChatParticipantRepository chatParticipantRepository,
            UserRepository userRepository,
            ChatRepository chatRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public Result<ChatParticipant> inviteUserToMultiChat(
            @NonNull ChatParticipant invitingChatParticipant,
            UUID inviteeUserId
    ) {
        // 1. Check if chat is Dialogue
        var chatId = invitingChatParticipant.getChatId();
        var chat = chatRepository.getChat(chatId);

        if (chat.getChatType() == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        // TODO: 09.05.2023 think about creating PermissionManager class
        // 2. Check permissions
        try {
            checkRule(new HasRequiredPermissionsRule(invitingChatParticipant, List.of(Permission.INVITE_TO_CHAT)));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        var result = performCommonChatInvitingChecks(chat, inviteeUserId);
        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        // TODO: 09.05.2023 think about moving creation of ChatParticipant to Chat
        // 3. Get 'everyone' role
        var everyoneRole = chat.getEveryoneRole();
        var roles = new HashSet<ChatRole>();
        roles.add(everyoneRole);

        return Result.success(ChatParticipant.createNew(
                userRepository.getUser(inviteeUserId),
                chatId,
                roles
        ));
    }

    @Override
    public Result<ChatParticipant> addUserToDialogue(UUID chatId, UUID userId) {
        // 1. Check if chat is Multi
        var chat = chatRepository.getChat(chatId);

        if (chat.getChatType() == ChatType.MULTI) {
            return Result.failure(IllegalChatTypeException.ofMulti());
        }

        var result = performCommonChatInvitingChecks(chat, userId);
        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        // 2. Get 'everyone' role
        var everyoneRole = chat.getEveryoneRole();
        var roles = new HashSet<ChatRole>();
        roles.add(everyoneRole);

        return Result.success(ChatParticipant.createNew(
                userRepository.getUser(userId),
                chatId,
                roles
        ));
    }

    @Override
    public Result<Unit> kickFromChat(
            @NonNull ChatParticipant deletingChatParticipant,
            ChatParticipant beingDeletedChatParticipant
    ) {
        // 1. Check if participants is in the same chat
        if (!deletingChatParticipant.isSameChat(beingDeletedChatParticipant)) {
            return Result.failure(new UserDoesNotExistsInChatException(
                    beingDeletedChatParticipant.getChatParticipantId(),
                    deletingChatParticipant.getChatId()
            ));
        }

        // 2. Check if chat type is Dialogue
        if (chatRepository.getChat(deletingChatParticipant.getChatId()).getChatType() == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        // 3. Check if deleting user has enough permissions
        try {
            checkRule(new HasRequiredPermissionsRule(deletingChatParticipant, List.of(Permission.INVITE_TO_CHAT)));
        } catch (BusinessRuleViolationException e) {
            return Result.failure(e);
        }

        chatParticipantRepository.delete(beingDeletedChatParticipant);

        return Result.success();
    }

    @Override
    public Result<Unit> leaveFromChat(@NonNull ChatParticipant chatParticipant) {
        // Check if chat type is Dialogue
        if (chatRepository.getChat(chatParticipant.getChatId()).getChatType() == ChatType.DIALOGUE) {
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
