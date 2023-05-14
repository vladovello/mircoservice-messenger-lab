package com.messenger.chat.application.query;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}
