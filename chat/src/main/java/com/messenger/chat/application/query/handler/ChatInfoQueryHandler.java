package com.messenger.chat.application.query.handler;

import com.messenger.chat.application.command.exception.UnexpectedException;
import com.messenger.chat.application.query.ChatInfoQuery;
import com.messenger.chat.application.query.QueryHandler;
import com.messenger.chat.application.query.dto.ChatInfoDto;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.exception.ChatParticipantNotFoundException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ChatInfoQueryHandler implements QueryHandler<ChatInfoQuery, Result<ChatInfoDto>> {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRepository chatRepository;

    public ChatInfoQueryHandler(ChatParticipantRepository chatParticipantRepository, ChatRepository chatRepository) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.chatRepository = chatRepository;
    }

    /**
     * @param query CQS query for appropriate handler
     * @return {@code Result<ChatInfoDto>} describing whether the result of the function execution was successful.
     * @implNote This method can return {@code Result} with either:<br>
     * {@code ChatNotFoundException},<br>
     * {@code ChatParticipantNotFoundException}
     */
    @Override
    public Result<ChatInfoDto> handle(@NonNull ChatInfoQuery query) {
        var optionalChat = chatRepository.getChat(query.getChatId());

        if (optionalChat.isEmpty()) {
            return Result.failure(new ChatNotFoundException(query.getChatId()));
        }

        if (!chatParticipantRepository.isUserBelongsToChat(query.getChatId(), query.getRequesterId())) {
            return Result.failure(new ChatParticipantNotFoundException(query.getChatId(), query.getRequesterId()));
        }

        var chat = optionalChat.get();
        ChatInfoDto chatInfoDto;

        if (chat.getChatType() == ChatType.DIALOGUE) {
            var participants = chatParticipantRepository.getAllByChatId(chat.getId());
            var secondDialogueUser = participants
                    .stream()
                    .filter(chatParticipant -> chatParticipant.getUserId() != query.getRequesterId())
                    .findFirst().get();

            chatInfoDto = new ChatInfoDto(
                    secondDialogueUser.getChatUser().getFullName().getValue(),
                    secondDialogueUser.getChatUser().getAvatarId(),
                    chat.getCreationDate()
            );
        } else {
            var optionalChatOwner = chatParticipantRepository.getChatOwner(chat.getId());

            if (optionalChatOwner.isEmpty()) {
                throw new UnexpectedException(String.format("Multi chat with id '%s' has no chat owner", chat.getId()));
            }

            var chatOwner = optionalChatOwner.get();

            chatInfoDto = new ChatInfoDto(
                    chat.getChatName().getName(),
                    chat.getAvatarId(),
                    chat.getCreationDate(),
                    chatOwner.getUserId()
            );
        }

        return Result.success(chatInfoDto);
    }
}
