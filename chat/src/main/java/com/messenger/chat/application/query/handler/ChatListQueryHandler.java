package com.messenger.chat.application.query.handler;

import com.messenger.chat.application.query.ChatListQuery;
import com.messenger.chat.application.query.QueryHandler;
import com.messenger.chat.application.query.dto.PreviewChatInfoListDto;
import com.messenger.chat.application.query.repository.ChatQueryRepository;
import lombok.NonNull;

public class ChatListQueryHandler implements QueryHandler<ChatListQuery, PreviewChatInfoListDto> {
    private final ChatQueryRepository chatQueryRepository;

    public ChatListQueryHandler(ChatQueryRepository chatQueryRepository) {
        this.chatQueryRepository = chatQueryRepository;
    }

    @Override
    public PreviewChatInfoListDto handle(@NonNull ChatListQuery query) {
        return new PreviewChatInfoListDto(
                query.getPageNumber(),
                query.getPageSize(),
                chatQueryRepository.getChatsPaginated(
                        query.getPageNumber(),
                        query.getPageSize(),
                        query.getChatName()
                )
        );
    }
}
