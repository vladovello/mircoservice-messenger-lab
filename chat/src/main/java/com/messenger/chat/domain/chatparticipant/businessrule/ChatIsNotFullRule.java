package com.messenger.chat.domain.chatparticipant.businessrule;

import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.sharedlib.domain.ddd.BusinessRule;

public class ChatIsNotFullRule implements BusinessRule {
    private static final int MAX_MULTI_PARTICIPANTS_COUNT = 1_000_000;

    private final ChatType chatType;
    private final int participantsCount;

    public ChatIsNotFullRule(ChatType chatType, int participantsCount) {
        this.chatType = chatType;
        this.participantsCount = participantsCount;
    }

    @Override
    public boolean isComplies() {
        if (chatType == ChatType.DIALOGUE) {
            return participantsCount < 2;
        } else if (chatType == ChatType.MULTI) {
            return participantsCount < MAX_MULTI_PARTICIPANTS_COUNT;
        }

        return true;
    }

    @Override
    public String ruleViolationMessage() {
        return chatType == ChatType.DIALOGUE ? "Dialogue can have only two members" : String.format(
                "Multi person chat can only have up to %d persons",
                MAX_MULTI_PARTICIPANTS_COUNT
        );
    }
}
