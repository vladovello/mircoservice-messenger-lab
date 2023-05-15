package com.messenger.chat.application;

public interface Handler<I, O> {
    O handle(I query);
}
