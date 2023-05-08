package com.messenger.chat.domain.chatparticipant.businessrule;

import com.messenger.chat.domain.chat.Permission;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.sharedlib.ddd.domain.BusinessRule;

import java.util.List;

public class HasRequiredPermissionsRule implements BusinessRule {
    private final ChatParticipant chatParticipant;
    private final List<Permission> permissions;

    public HasRequiredPermissionsRule(ChatParticipant chatParticipant, List<Permission> permissions) {
        this.chatParticipant = chatParticipant;
        this.permissions = permissions;
    }

    @Override
    public boolean isComplies() {
        return chatParticipant.getChatRoles().stream().anyMatch(chatRole -> chatRole.hasPermissions(permissions));
    }

    @Override
    public String ruleViolationMessage() {
        return "User cannot perform this action because he doesn't have enough permissions";
    }
}
