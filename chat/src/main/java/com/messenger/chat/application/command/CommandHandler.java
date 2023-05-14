package com.messenger.chat.application.command;

public interface CommandHandler<C, R> {
    R handle(C command);
}
