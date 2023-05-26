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

public interface ChatDomainService {
    /**
     * Creates multi chat
     * @param chatName name of the chat
     * @param avatarId avatar id of the chat
     * @param ownerId user with owner id owning the chat
     * @param participantIds participants of this chat invited by owner
     * @return {@code Chat}
     */
    Chat createMultiChat(ChatName chatName, UUID avatarId, UUID ownerId, List<UUID> participantIds);

    /**
     * Creates dialogue chat
     * @param userId1 first user participating in chat
     * @param userId2 second user participating in chat
     * @return {@code Result<Chat>} describing whether the result of the function execution was successful.
     */
    Result<Chat> createDialogueChat(UUID userId1, UUID userId2);

    Chat createChatWithDefaultAvatar(ChatType chatType, ChatName chatName);

    boolean changeChatAvatar(Chat chat, UUID avatarId);

    Result<ChatParticipant> addUserToMultiChat(@NonNull ChatParticipant invitingChatParticipant, UUID inviteeUserId);

    Result<Unit> kickFromMultiChat(@NonNull ChatParticipant deletingChatParticipant, ChatParticipant beingDeletedChatParticipant);

    Result<Unit> leaveFromMultiChat(ChatParticipant chatParticipant);
}
