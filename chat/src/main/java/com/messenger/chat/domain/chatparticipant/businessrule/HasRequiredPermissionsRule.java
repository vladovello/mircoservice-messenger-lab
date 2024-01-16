package com.messenger.chat.domain.chatparticipant.businessrule;

import com.messenger.chat.domain.chat.Permission;
import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.sharedlib.domain.ddd.BusinessRule;

import java.util.Set;

public class HasRequiredPermissionsRule implements BusinessRule {
    private final ChatParticipant chatParticipant;
    private final Set<Permission> permissions;

    public HasRequiredPermissionsRule(ChatParticipant chatParticipant, Set<Permission> permissions) {
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
