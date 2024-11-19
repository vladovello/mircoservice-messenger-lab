package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.SendMessageInDialogueCommand;
import com.messenger.chat.application.command.exception.UserBlacklistedException;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.chat.domain.user.exception.UserDoesNotExistsException;
import com.messenger.chat.domain.user.repository.BlacklistRepository;
import com.messenger.chat.domain.user.repository.UserRepository;
import com.messenger.sharedlib.util.Result;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Были использованы: структурированное базисное тестирование (каждый блок, добавляющий нелинейность, протестирован)
class SendMessageInDialogueCommandHandlerTest {

    private MessageDomainService messageDomainService;
    private BlacklistRepository blacklistRepository;
    private ChatDomainService chatDomainService;
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    private SendMessageInDialogueCommandHandler handler;

    @BeforeEach
    void setUp() {
        messageDomainService = mock(MessageDomainService.class);
        blacklistRepository = mock(BlacklistRepository.class);
        chatDomainService = mock(ChatDomainService.class);
        chatRepository = mock(ChatRepository.class);
        userRepository = mock(UserRepository.class);

        handler = new SendMessageInDialogueCommandHandler(
                messageDomainService,
                blacklistRepository,
                chatDomainService,
                chatRepository,
                userRepository
        );
    }

    @Test
    void handle_recipientDoesNotExists_shouldReturnFailureWithUserDoesNotExistsException() {
        // given
        when(userRepository.isUserExists(any())).thenReturn(false);

        // when
        var result = handler.handle(emptyCommandMock());

        // then
        assertTrue(result.isFailure());
        assertInstanceOf(UserDoesNotExistsException.class, result.exceptionOrNull());
    }

    @Test
    void handle_noChatBetweenUsers_and_failedToCreateChat_shouldReturnFailure() {
        // given
        when(userRepository.isUserExists(any())).thenReturn(true);
        var emptyOptional = Optional.<Chat>empty();
        when(chatRepository.getDialogue(any(), any())).thenReturn(emptyOptional);
        when(chatDomainService.createDialogueChat(any(), any())).thenReturn(Result.failure(new Exception()));

        // when
        var result = handler.handle(emptyCommandMock());

        // then
        assertTrue(result.isFailure());
    }

    @Test
    void handle_noChatBetweenUsers_shouldCreateChat() {
        // given
        var command = testCommandNoAttachments();

        when(userRepository.isUserExists(command.getRecipientId())).thenReturn(true);
        var emptyOptional = Optional.<Chat>empty();
        when(chatRepository.getDialogue(command.getSenderId(), command.getRecipientId())).thenReturn(emptyOptional);

        var chat = mock(Chat.class);
        when(chat.getId()).thenReturn(UUID.randomUUID());
        var createdChatResult = Result.success(chat);
        when(chatDomainService.createDialogueChat(command.getSenderId(), command.getRecipientId())).thenReturn(
                createdChatResult);

        when(blacklistRepository.isUserIsBlacklistedByOther(
                command.getSenderId(),
                command.getRecipientId()
        )).thenReturn(false);

        when(messageDomainService.assignAttachments(any(), any())).thenReturn(Result.success());

        // when
        var result = handler.handle(command);

        // then
        assertTrue(result.isSuccess());
        verify(chatDomainService).createDialogueChat(any(), any());
    }

    @Test
    void handle_senderIsBlacklistedByRecipient_shouldReturnFailureWithUserBlacklistedException() {
        // given
        var command = testCommandNoAttachments();

        when(userRepository.isUserExists(command.getRecipientId())).thenReturn(true);

        var chat = mock(Chat.class);
        when(chat.getId()).thenReturn(UUID.randomUUID());
        var chatOptional = Optional.of(chat);
        when(chatRepository.getDialogue(command.getSenderId(), command.getRecipientId())).thenReturn(chatOptional);

        when(blacklistRepository.isUserIsBlacklistedByOther(
                command.getSenderId(),
                command.getRecipientId()
        )).thenReturn(true);

        // when
        var result = handler.handle(command);

        // then
        assertTrue(result.isFailure());
        assertInstanceOf(UserBlacklistedException.class, result.exceptionOrNull());
    }

    @Test
    void handle_createMessageTextFailed_shouldReturnFailure() {
        // given
        var command = testCommandNoAttachments();

        when(userRepository.isUserExists(command.getRecipientId())).thenReturn(true);

        var chat = mock(Chat.class);
        when(chat.getId()).thenReturn(UUID.randomUUID());
        var chatOptional = Optional.of(chat);
        when(chatRepository.getDialogue(command.getSenderId(), command.getRecipientId())).thenReturn(chatOptional);

        when(blacklistRepository.isUserIsBlacklistedByOther(
                command.getSenderId(),
                command.getRecipientId()
        )).thenReturn(false);

        command.setMessageText("");

        // when
        var result = handler.handle(command);

        // then
        assertTrue(result.isFailure());
    }

    @Test
    void handle_everythingValid_shouldReturnSuccess() {
        // given
        var command = testCommandNoAttachments();

        when(userRepository.isUserExists(command.getRecipientId())).thenReturn(true);

        var chat = mock(Chat.class);
        when(chat.getId()).thenReturn(UUID.randomUUID());
        var chatOptional = Optional.of(chat);
        when(chatRepository.getDialogue(command.getSenderId(), command.getRecipientId())).thenReturn(chatOptional);

        when(blacklistRepository.isUserIsBlacklistedByOther(
                command.getSenderId(),
                command.getRecipientId()
        )).thenReturn(false);

        when(messageDomainService.assignAttachments(any(), any())).thenReturn(Result.success());

        // when
        var result = handler.handle(command);

        // then
        assertTrue(result.isSuccess());
        verify(chatRepository).getDialogue(command.getSenderId(), command.getRecipientId());
        verify(messageDomainService).assignAttachments(any(), any());
    }

    private static @NotNull SendMessageInDialogueCommand testCommandNoAttachments() {
        return new SendMessageInDialogueCommand(UUID.randomUUID(), UUID.randomUUID(), "test", List.of());
    }

    private static @NotNull SendMessageInDialogueCommand emptyCommandMock() {
        return mock(SendMessageInDialogueCommand.class);
    }
}
