package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.SendMessageInDialogueCommand;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.chat.domain.user.repository.BlacklistRepository;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;

public class SendMessageInDialogueCommandHandler implements CommandHandler<SendMessageInDialogueCommand, Result<Unit>> {
    private final MessageDomainService messageDomainService;
    private final BlacklistRepository blacklistRepository;
    private final ChatDomainService chatDomainService;
    private final ChatRepository chatRepository;

    public SendMessageInDialogueCommandHandler(
            MessageDomainService messageDomainService,
            BlacklistRepository blacklistRepository,
            ChatDomainService chatDomainService,
            ChatRepository chatRepository
    ) {
        this.messageDomainService = messageDomainService;
        this.blacklistRepository = blacklistRepository;
        this.chatDomainService = chatDomainService;
        this.chatRepository = chatRepository;
    }

    // 1. Проверить, что чат существует м/у пользователями. Если нет, то создать чат
    // 2. Проверить, может ли пользователь отправлять другому сообщения. Если нет, то выйти из функции
    // 3. Сохранить сообщение и выйти из функции
    @Override
    public Result<Unit> handle(@NonNull SendMessageInDialogueCommand command) {
        var optionalChat = chatRepository.getDialogue(command.getSenderId(), command.getRecipientId());
        Chat chat;

        if (optionalChat.isPresent()) {
            chat = optionalChat.get();
        } else {
            var result = chatDomainService.createDialogueChat(command.getSenderId(), command.getRecipientId());

            if (result.isFailure()) {
                return Result.failure(result.exceptionOrNull());
            }

            chat = result.getOrNull();
        }

        if (blacklistRepository.isUserIsBlacklistedByOther(command.getSenderId(), command.getRecipientId())) {
            return Result.failure(new Exception());
        }

        var messageText = MessageText.create(command.getMessageText());

        if (messageText.isFailure()) {
            return Result.failure(new Exception());
        }

        var message = Message.createNew(chat.getId(), command.getSenderId(), messageText.getOrNull());

        if (message.isFailure()) {
            return Result.failure(new Exception());
        }

        var result = messageDomainService.sendMessage(message.getOrNull());

        if (result.isFailure()) {
            return Result.failure(new Exception());
        }

        return Result.success();
    }
}
