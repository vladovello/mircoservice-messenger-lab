package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.SendMessageInMultiCommand;
import com.messenger.chat.domain.chat.exception.ChatNotFoundException;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SendMessageInMultiCommandHandler implements CommandHandler<SendMessageInMultiCommand, Result<Unit>> {
    private final ChatRepository chatRepository;
    private final MessageDomainService messageDomainService;

    public SendMessageInMultiCommandHandler(
            ChatRepository chatRepository,
            MessageDomainService messageDomainService
    ) {
        this.chatRepository = chatRepository;
        this.messageDomainService = messageDomainService;
    }

    // 1. Проверить, что чат существует
    // 2. Отправить сообщение через доменный сервис

    /**
     * @param command CQS command for appropriate handler
     * @return {@code Result<Unit>} describing whether the result of the function execution was successful
     * @implNote This method can return {@code Result} with either:<br>
     * {@code BusinessRuleViolationException},<br>
     * {@code ChatNotFoundException},<br>
     * {@code AttachmentNotFoundInStorageException}
     */
    @Override
    public Result<Unit> handle(@NonNull SendMessageInMultiCommand command) {
        if (!chatRepository.isChatExists(command.getChatId())) {
            return Result.failure(new ChatNotFoundException(command.getChatId()));
        }

        var messageText = MessageText.create(command.getMessageText());

        if (messageText.isFailure()) {
            return Result.failure(messageText.exceptionOrNull());
        }

        var messageResult = Message.createNew(command.getChatId(), command.getSenderId(), messageText.getOrNull());

        if (messageResult.isFailure()) {
            return Result.failure(messageResult.exceptionOrNull());
        }

        var message = messageResult.getOrNull();
        var attachments = command.attachmentsToDomain();

        return messageDomainService.assignAttachments(message, attachments);
    }
}
