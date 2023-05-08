package com.messenger.chat.domain.chatparticipant.service;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;

import java.util.UUID;

public interface ChatParticipantDomainService {
    Result<ChatParticipant> inviteUserToMultiChat(ChatParticipant invitingChatParticipant, UUID inviteeUUID);

    Result<ChatParticipant> addUserToDialogue(UUID chatId, UUID userId);

    Result<Unit> kickFromChat(ChatParticipant deletingUserId, ChatParticipant beingDeletedUserId);

    Result<Unit> leaveFromChat(ChatParticipant chatParticipant);
}
