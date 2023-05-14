package com.messenger.chat.domain.chat;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.message.Message;
import lombok.NonNull;

public class PermissionManager {
    private PermissionManager() {
    }

    public static boolean canKickMember(@NonNull ChatParticipant subject, @NonNull ChatParticipant object) {
        if (subject.isNotInSameChat(object)) {
            return false;
        }

        return canPerformActionWithUser(subject, object, Permission.KICK_MEMBERS);
    }

    public static boolean canManageMessage(@NonNull ChatParticipant chatParticipant, @NonNull Message message) {
        if (!chatParticipant.getChatId().equals(message.getChatId())) {
            return false;
        }

        if (!chatParticipant.getUser().getUserId().equals(message.getSenderUserId())) {
            return false;
        }

        return canPerformIndependentAction(chatParticipant, Permission.MANAGE_MESSAGES);
    }

    public static boolean canManageRole(@NonNull ChatParticipant chatParticipant, @NonNull ChatRole role) {
        if (!chatParticipant.getChatId().equals(role.getChatId())) {
            return false;
        }

        var optionalHighestRole = chatParticipant.getHighestPriorityRole();
        return optionalHighestRole.filter(chatRole -> chatRole.getPriority() > role.getPriority()).isPresent();
    }

    public static boolean canManageChat(@NonNull ChatParticipant chatParticipant) {
        return canPerformIndependentAction(chatParticipant, Permission.MANAGE_CHAT);
    }

    private static boolean canPerformIndependentAction(@NonNull ChatParticipant subject, Permission permission) {
        return subject.hasPermission(permission);
    }

    private static boolean canPerformActionWithUser(
            @NonNull ChatParticipant subject,
            ChatParticipant object,
            Permission permission
    ) {
        if (subject.isNotInSameChat(object)) {
            return false;
        }

        var optionalSubjectRole = subject.getHighestPriorityRoleWithPermission(permission);
        if (optionalSubjectRole.isEmpty()) {
            return false;
        }

        var optionalObjectRole = object.getHighestPriorityRole();
        return optionalObjectRole
                .map(chatRole -> optionalSubjectRole.get().getPriority() > chatRole.getPriority())
                .orElse(true);

    }
}
