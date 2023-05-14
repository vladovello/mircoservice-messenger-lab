package com.messenger.chat.application.query.handler;

import com.messenger.chat.application.query.ChatMessagesPaginationQuery;
import com.messenger.chat.application.query.QueryHandler;
import com.messenger.chat.application.query.dto.MessageDto;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.message.repository.MessageRepository;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessagesPaginationQueryHandler implements
        QueryHandler<ChatMessagesPaginationQuery, Result<List<MessageDto>>> {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    public ChatMessagesPaginationQueryHandler(
            MessageRepository messageRepository, ChatRepository chatRepository,
            ChatParticipantRepository chatParticipantRepository
    ) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.chatParticipantRepository = chatParticipantRepository;
    }

    // TODO: 15.05.2023 create pipeline and move user belongs to chat and chat exists checks to one of pipes
    @Override
    public Result<List<MessageDto>> handle(@NonNull ChatMessagesPaginationQuery query) {
        if (!chatRepository.isChatExists(query.getChatId())) {
            return Result.failure(new ChatNotFoundException(query.getChatId()));
        }

        if (!chatParticipantRepository.isUserBelongsToChat(query.getChatId(), query.getRequesterId())) {
            return Result.failure(new UserDoesNotExistsInChatException(query.getRequesterId(), query.getChatId()));
        }

        var messages = messageRepository.getMessagesPaginated(query.getChatId(), query.getOffset());
        return Result.success(messages.stream().map(MessageDto::toMessageDto).collect(Collectors.toList()));
    }
}
