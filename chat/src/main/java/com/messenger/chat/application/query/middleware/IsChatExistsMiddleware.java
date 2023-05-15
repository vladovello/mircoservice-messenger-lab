package com.messenger.chat.application.query.middleware;

import com.messenger.chat.application.pipeline.Middleware;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import lombok.NonNull;

public class IsChatExistsMiddleware implements Middleware {
    private final ChatRepository chatRepository;

    public IsChatExistsMiddleware(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public <I, O> O execute(I input, @NonNull Next<O> next) {
//        if (input instanceof ChatUser && (chatRepository.isChatExists(((ChatUser) input).getChatId()))) {
//            return next.execute();
//        }
//
        return null;
    }
}
