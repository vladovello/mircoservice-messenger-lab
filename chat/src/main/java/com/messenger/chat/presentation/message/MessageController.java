package com.messenger.chat.presentation.message;

import com.messenger.chat.application.command.SendMessageInDialogueCommand;
import com.messenger.chat.application.command.SendMessageInMultiCommand;
import com.messenger.chat.application.command.exception.UserBlacklistedException;
import com.messenger.chat.application.command.handler.SendMessageInDialogueCommandHandler;
import com.messenger.chat.application.command.handler.SendMessageInMultiCommandHandler;
import com.messenger.chat.application.query.ChatMessagesPaginationQuery;
import com.messenger.chat.application.query.MessageListQuery;
import com.messenger.chat.application.query.dto.PreviewChatInfoListDto;
import com.messenger.chat.application.query.handler.ChatMessagesPaginationQueryHandler;
import com.messenger.chat.application.query.handler.MessageListQueryHandler;
import com.messenger.chat.domain.chatparticipant.exception.UserDoesNotExistsInChatException;
import com.messenger.chat.presentation.chat.dto.CreateMessageDialogueDto;
import com.messenger.chat.presentation.chat.dto.CreateMessageMultiDto;
import com.messenger.security.jwt.PayloadPrincipal;
import com.messenger.sharedlib.exception.NotFoundException;
import com.messenger.sharedlib.presentation.errorhandling.ApiError;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/chats/")
@RouterOperation
public class MessageController {
    private final MessageListQueryHandler messageListQueryHandler;
    private final ChatMessagesPaginationQueryHandler chatMessagesPaginationQueryHandler;
    private final SendMessageInDialogueCommandHandler sendMessageInDialogueCommandHandler;
    private final SendMessageInMultiCommandHandler sendMessageInMultiCommandHandler;

    public MessageController(
            MessageListQueryHandler messageListQueryHandler,
            ChatMessagesPaginationQueryHandler chatMessagesPaginationQueryHandler,
            SendMessageInDialogueCommandHandler sendMessageInDialogueCommandHandler,
            SendMessageInMultiCommandHandler sendMessageInMultiCommandHandler
    ) {
        this.messageListQueryHandler = messageListQueryHandler;
        this.chatMessagesPaginationQueryHandler = chatMessagesPaginationQueryHandler;
        this.sendMessageInDialogueCommandHandler = sendMessageInDialogueCommandHandler;
        this.sendMessageInMultiCommandHandler = sendMessageInMultiCommandHandler;
    }

    @GetMapping("search")
    public ResponseEntity<PreviewChatInfoListDto> getMessageList(
            int pageNumber,
            int pageSize,
            String messageText,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var previewChatInfoListDto = messageListQueryHandler.handle(new MessageListQuery(
                principal.getId(),
                pageNumber,
                pageSize,
                messageText
        ));

        return ResponseEntity.ok(previewChatInfoListDto);
    }

    @GetMapping("chat/{chatId}")
    public ResponseEntity<Object> searchPaginated(
            @PathVariable UUID chatId,
            @RequestParam int offset,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = chatMessagesPaginationQueryHandler.handle(new ChatMessagesPaginationQuery(
                principal.getId(),
                chatId,
                offset
        ));

        return result.fold(ResponseEntity::ok, e -> {
            if (e instanceof NotFoundException) return ResponseEntity.notFound().build();
            else if (e instanceof UserDoesNotExistsInChatException)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return createBadRequestResponse(e.getMessage());
        });
    }

    @PostMapping("chat/dialogue/send")
    public ResponseEntity<Object> sendMessageInDialogue(
            @RequestBody @NonNull CreateMessageDialogueDto createMessageDialogueDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = sendMessageInDialogueCommandHandler.handle(new SendMessageInDialogueCommand(
                principal.getId(),
                createMessageDialogueDto.getRecipientId(),
                createMessageDialogueDto.getMessageText()
        ));

        return result.fold(ResponseEntity::ok, e -> {
            if (e instanceof UserDoesNotExistsInChatException)
                return ResponseEntity.internalServerError().body(new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                ));
            else if (e instanceof UserBlacklistedException)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            return createBadRequestResponse(e.getMessage());
        });
    }

    @PostMapping("chat/multi/send")
    public ResponseEntity<Object> sendMessageInMulti(
            @RequestBody @NonNull CreateMessageMultiDto createMessageMultiDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = sendMessageInMultiCommandHandler.handle(new SendMessageInMultiCommand(
                principal.getId(),
                createMessageMultiDto.getChatId(),
                createMessageMultiDto.getMessageText()
        ));

        return result.fold(ResponseEntity::ok, e -> {
            if (e instanceof UserDoesNotExistsInChatException)
                return ResponseEntity.internalServerError().body(new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                ));
            return createBadRequestResponse(e.getMessage());
        });
    }

    private static @NonNull ResponseEntity<Object> createBadRequestResponse(String message) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                        HttpStatus.BAD_REQUEST,
                        message
                ));
    }
}
