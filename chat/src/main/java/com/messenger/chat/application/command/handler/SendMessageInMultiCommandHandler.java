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
    @Override
    public Result<Unit> handle(@NonNull SendMessageInMultiCommand command) {
        if (!chatRepository.isChatExists(command.getChatId())) {
            return Result.failure(new ChatNotFoundException(command.getChatId()));
        }

        var messageText = MessageText.create(command.getMessageText());

        if (messageText.isFailure()) {
            return Result.failure(messageText.exceptionOrNull());
        }

        var message = Message.createNew(command.getChatId(), command.getSenderId(), messageText.getOrNull());

        if (message.isFailure()) {
            return Result.failure(message.exceptionOrNull());
        }

        var result = messageDomainService.sendMessage(message.getOrNull());

        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        return Result.success();
    }
}
