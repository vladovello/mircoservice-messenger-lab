package com.messenger.chat.application.query.handler;

import com.messenger.chat.application.query.MessageListQuery;
import com.messenger.chat.application.query.QueryHandler;
import com.messenger.chat.application.query.dto.PreviewChatInfoListDto;
import com.messenger.chat.application.query.repository.ChatQueryRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MessageListQueryHandler implements QueryHandler<MessageListQuery, PreviewChatInfoListDto> {
    private final ChatQueryRepository chatQueryRepository;

    public MessageListQueryHandler(ChatQueryRepository chatQueryRepository) {
        this.chatQueryRepository = chatQueryRepository;
    }

    @Override
    public PreviewChatInfoListDto handle(@NonNull MessageListQuery query) {
        return new PreviewChatInfoListDto(
                query.getPageNumber(),
                query.getPageSize(),
                chatQueryRepository.getUserMessagesPaginated(
                        query.getRequesterId(),
                        query.getPageNumber(),
                        query.getPageSize(),
                        query.getMessageText()
                )
        );
    }
}
