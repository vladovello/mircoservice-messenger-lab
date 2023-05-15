package com.messenger.chat.application.pipeline;

import lombok.NonNull;

@FunctionalInterface
public interface Middleware {
    <I, O> O execute(I input, @NonNull Next<O> next);

    @FunctionalInterface
    interface Next<T> {
        T execute();
    }
}
