package com.messenger.chat.application.command.exception;

import java.util.UUID;

public class UserBlacklistedException extends Exception {
    public UserBlacklistedException(UUID blacklistedId, UUID blacklisterId) {
        super(String.format("User with id '%s' is banned by user with id '%s'", blacklistedId, blacklisterId));
    }
}
