package com.messenger.chat.domain.chatparticipant.exception;

import com.messenger.sharedlib.ddd.domain.BusinessRuleViolationException;

public class NotEnoughPermissionsException extends BusinessRuleViolationException {
    public <T> NotEnoughPermissionsException(T userId) {
        super(String.format("User with id '%s' doesn't have enough permissions to perform this action", userId));
    }
}
