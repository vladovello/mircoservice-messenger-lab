package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.ChangeMultiChatCommand;
import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.chatparticipant.exception.ChatParticipantNotFoundException;
import com.messenger.chat.domain.chatparticipant.exception.IllegalChatTypeException;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ChangeMultiChatCommandHandler implements CommandHandler<ChangeMultiChatCommand, Result<Unit>> {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatDomainService chatDomainService;
    private final ChatRepository chatRepository;

    public ChangeMultiChatCommandHandler(
            ChatParticipantRepository chatParticipantRepository,
            ChatDomainService chatDomainService,
            ChatRepository chatRepository
    ) {
        this.chatParticipantRepository = chatParticipantRepository;
        this.chatDomainService = chatDomainService;
        this.chatRepository = chatRepository;
    }

    /**
     * @param command CQS command for appropriate handler
     * @return {@code Result<Unit>} describing whether the result of the function execution was successful
     * @implNote This method can return {@code Result} with either:<br>
     * {@code ChatNotFoundException},<br>
     * {@code IllegalChatTypeException},<br>
     * {@code ChatParticipantNotFoundException}
     */
    @Override
    public Result<Unit> handle(@NonNull ChangeMultiChatCommand command) {
        var optionalChat = chatRepository.getChat(command.getChatId());

        if (optionalChat.isEmpty()) {
            return Result.failure(new ChatNotFoundException(command.getChatId()));
        }

        var chat = optionalChat.get();

        if (chat.getChatType() == ChatType.DIALOGUE) {
            return Result.failure(IllegalChatTypeException.ofDialogue());
        }

        var optionalActorChatParticipant = chatParticipantRepository.getChatParticipant(
                command.getChatId(),
                command.getActorId()
        );

        if (optionalActorChatParticipant.isEmpty()) {
            return Result.failure(new ChatParticipantNotFoundException(command.getChatId(), command.getActorId()));
        }

        var actorChatParticipant = optionalActorChatParticipant.get();

        var chatName = ChatName.create(command.getChatName());

        if (chatName.isFailure()) {
            return Result.failure(chatName.exceptionOrNull());
        }

        chat.changeChat(chatName.getOrNull(), command.getAvatarId());

        if (command.getUsersList() != null) {
            command.getUsersList().forEach(uuid -> chatDomainService.addUserToMultiChat(
                    actorChatParticipant,
                    uuid
            ));
        }

        chatRepository.save(chat);

        return Result.success();
    }
}
