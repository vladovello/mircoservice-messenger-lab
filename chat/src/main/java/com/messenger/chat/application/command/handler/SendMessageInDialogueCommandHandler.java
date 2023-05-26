package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.SendMessageInDialogueCommand;
import com.messenger.chat.application.command.exception.UserBlacklistedException;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.service.AttachmentDomainService;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.chat.domain.message.valueobject.MessageText;
import com.messenger.chat.domain.user.exception.UserDoesNotExistsException;
import com.messenger.chat.domain.user.repository.BlacklistRepository;
import com.messenger.chat.domain.user.repository.UserRepository;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SendMessageInDialogueCommandHandler implements CommandHandler<SendMessageInDialogueCommand, Result<Unit>> {
    private final MessageDomainService messageDomainService;
    private final BlacklistRepository blacklistRepository;
    private final ChatDomainService chatDomainService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final AttachmentDomainService attachmentDomainService;

    public SendMessageInDialogueCommandHandler(
            MessageDomainService messageDomainService,
            BlacklistRepository blacklistRepository,
            ChatDomainService chatDomainService,
            ChatRepository chatRepository,
            UserRepository userRepository,
            AttachmentDomainService attachmentDomainService
    ) {
        this.messageDomainService = messageDomainService;
        this.blacklistRepository = blacklistRepository;
        this.chatDomainService = chatDomainService;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.attachmentDomainService = attachmentDomainService;
    }

    // 1. Проверить, что чат существует м/у пользователями. Если нет, то создать чат
    // 2. Проверить, может ли пользователь отправлять другому сообщения. Если нет, то выйти из функции
    // 3. Сохранить сообщение и выйти из функции
    /**
     * @param command CQS command for appropriate handler
     * @return {@code Result<Unit>} describing whether the result of the function execution was successful
     * @implNote This method can return {@code Result} with either:<br>
     * {@code BusinessRuleViolationException},<br>
     * {@code UserDoesNotExistsException},<br>
     * {@code UserBlacklistedException}
     */
    @Override
    public Result<Unit> handle(@NonNull SendMessageInDialogueCommand command) {
        if (!userRepository.isUserExists(command.getRecipientId())) {
            return Result.failure(new UserDoesNotExistsException(command.getRecipientId()));
        }

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
            return Result.failure(new UserBlacklistedException(command.getSenderId(), command.getRecipientId()));
        }

        var messageText = MessageText.create(command.getMessageText());

        if (messageText.isFailure()) {
            return Result.failure(messageText.exceptionOrNull());
        }

        var messageResult = Message.createNew(chat.getId(), command.getSenderId(), messageText.getOrNull());

        if (messageResult.isFailure()) {
            return Result.failure(messageResult.exceptionOrNull());
        }

        var message = messageResult.getOrNull();

        var attachmentsResult = attachmentDomainService.createAttachments(command.getAttachments(), message.getId());

        if (attachmentsResult.isFailure()) {
            return Result.failure(attachmentsResult.exceptionOrNull());
        }

        var attachments = attachmentsResult.getOrNull();

        message.assignAttachments(attachments);

        var result = messageDomainService.saveMessage(message);

        if (result.isFailure()) {
            return Result.failure(result.exceptionOrNull());
        }

        return Result.success();
    }
}
