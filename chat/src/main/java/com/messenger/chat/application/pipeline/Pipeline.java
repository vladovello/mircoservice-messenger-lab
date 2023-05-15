package com.messenger.chat.application.pipeline;

@FunctionalInterface
public interface Pipeline<I, O> {
    O execute(I input);
}
