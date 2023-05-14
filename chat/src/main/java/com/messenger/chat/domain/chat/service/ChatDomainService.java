package com.messenger.chat.domain.chat.service;

import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

// TODO: 13.05.2023 вынести методы, тесно связанные с чатом из других сервисов в этот
public interface ChatDomainService {
    Chat createMultiChat(ChatName chatName, UUID avatarId, UUID ownerId, List<UUID> participantIds);

    Result<Chat> createDialogueChat(UUID userId1, UUID userId2);

    Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName);

    boolean changeChatAvatar(Chat chat, UUID avatarId);

    Result<ChatParticipant> addUserToMultiChat(@NonNull ChatParticipant invitingChatParticipant, UUID inviteeUserId);

    Result<Unit> kickFromMultiChat(@NonNull ChatParticipant deletingChatParticipant, ChatParticipant beingDeletedChatParticipant);

    Result<Unit> leaveFromMultiChat(ChatParticipant chatParticipant);
}
