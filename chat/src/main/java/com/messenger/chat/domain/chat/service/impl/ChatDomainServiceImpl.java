package com.messenger.chat.domain.chat.service.impl;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.PermissionManager;
import com.messenger.chat.domain.chat.exception.ChatAlreadyExistsException;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.chatparticipant.businessrule.ChatIsNotFullRule;
import com.messenger.chat.domain.chatparticipant.exception.IllegalChatTypeException;
import com.messenger.chat.domain.chatparticipant.exception.NotEnoughPermissionsException;
import com.messenger.chat.domain.chatparticipant.exception.UserAlreadyInChatException;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;
import com.messenger.sharedlib.ddd.domain.DomainService;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public class ChatDomainServiceImpl extends DomainService implements ChatDomainService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRepository chatRepository;

    public ChatDomainServiceImpl(
            ChatParticipantRepository chatParticipantRepository,
            ChatRepository chatRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    @Transactional
    public Chat createMultiChat(ChatName chatName, UUID ownerId, UUID avatarId, @NonNull List<UUID> participantIds) {
        var chat = Chat.createMulti(avatarId, chatName);
        var owner = ChatParticipant.createNew(ownerId, chat.getId(), chat.getOwnerRole().get(), chat.getEveryoneRole());

        for (var participantId : participantIds) {
            var chatParticipant = ChatParticipant.createNew(
                    participantId,
                    chat.getId(),
                    chat.getEveryoneRole()
            );

            chatParticipantRepository.save(chatParticipant);
        }

        // INFO: maybe this saves is redundant
        chatParticipantRepository.save(owner);
        chatRepository.save(chat);

        return chat;
    }

    // TODO: 13.05.2023 generate default avatar
    @Override
    @Transactional
    public Result<Chat> createDialogueChat(UUID userId1, UUID userId2) {
        if (!chatRepository.isDialogueExists(userId1, userId2)) {
            return Result.failure(new ChatAlreadyExistsException(userId1, userId2));
        }

        var chat = Chat.createDialogue();

        chatRepository.save(chat);

        chatParticipantRepository.save(ChatParticipant.createNew(userId1, chat.getId(), chat.getEveryoneRole()));
        chatParticipantRepository.save(ChatParticipant.createNew(userId2, chat.getId(), chat.getEveryoneRole()));

        return Result.success(chat);
    }

    @Override
    public Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName) {
        return null;
    }

    @Override
    public boolean changeChatAvatar(Chat chat, UUID avatarId) {
        return false;
    }

    @Override
    public Result<ChatParticipant> addUserToMultiChat(
            @NonNull ChatParticipant invitingChatParticipant,
            UUID inviteeUserId
    ) {
        var chatId = invitingChatParticipant.getChatId();
        var optionalChat = chatRepository.getChat(chatId);

        if (optionalChat.isEmpty()) {
            return Result.failure(new ChatNotFoundException(chatId));
        }

        var chat = optionalChat.get();

        // 1. Check if chat is Dialogue
        if (chat.getChatType() == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        // 2. Common checks for all chats
        var result = performCommonChatInvitingChecks(chat, inviteeUserId);
        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        var chatParticipant = ChatParticipant.createNew(
                inviteeUserId,
                chatId,
                chat.getEveryoneRole()
        );
        chatParticipantRepository.save(chatParticipant);

        return Result.success(chatParticipant);
    }

    @Override
    public Result<Unit> kickFromMultiChat(
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
            return Result.failure(new NotEnoughPermissionsException(deletingChatParticipant.getChatUser().getUserId()));
        }

        chatParticipantRepository.delete(beingDeletedChatParticipant);

        return Result.success();
    }

    @Override
    public Result<Unit> leaveFromMultiChat(@NonNull ChatParticipant chatParticipant) {
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
        if (chatParticipantRepository.isUserBelongsToChat(chatId, inviteeId)) {
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
