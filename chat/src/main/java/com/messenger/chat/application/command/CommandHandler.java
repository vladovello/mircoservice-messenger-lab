package com.messenger.chat.application.command;

public interface CommandHandler<U, R> {
    R handle(U command);
}
