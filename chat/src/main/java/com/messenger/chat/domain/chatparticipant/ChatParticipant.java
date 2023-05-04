package com.messenger.chat.domain.chatparticipant;

import com.messenger.chat.domain.AvatarId;
import com.messenger.chat.domain.UserId;
import com.messenger.chat.domain.valueobject.FullName;

public class ChatParticipant {
    private ChatParticipantId chatParticipantId;
    private UserId userId;
    private FullName fullName;
    private AvatarId avatarId;
}
