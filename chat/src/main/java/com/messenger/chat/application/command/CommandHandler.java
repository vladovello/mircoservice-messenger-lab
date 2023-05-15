package com.messenger.chat.application.command;

@FunctionalInterface
public interface CommandHandler<C, R> {
    R handle(C command);
}
