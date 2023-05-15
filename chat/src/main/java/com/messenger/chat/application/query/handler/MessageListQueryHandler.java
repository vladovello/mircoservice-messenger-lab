package com.messenger.chat.application.query.handler;

import com.messenger.chat.application.query.MessageListQuery;
import com.messenger.chat.application.query.QueryHandler;
import com.messenger.chat.application.query.dto.PreviewChatInfoListDto;
import com.messenger.chat.application.query.repository.MessageQueryRepository;

public class MessageListQueryHandler implements QueryHandler<MessageListQuery, PreviewChatInfoListDto> {
    private final MessageQueryRepository messageQueryRepository;

    public MessageListQueryHandler(MessageQueryRepository messageQueryRepository) {
        this.messageQueryRepository = messageQueryRepository;
    }

    @Override
    public PreviewChatInfoListDto handle(MessageListQuery query) {
        return new PreviewChatInfoListDto(
                query.getPageNumber(),
                query.getPageSize(),
                messageQueryRepository.getMessagesPaginated(
                        query.getRequesterId(),
                        query.getPageNumber(),
                        query.getPageSize(),
                        query.getMessageText()
                )
        );
    }
}
