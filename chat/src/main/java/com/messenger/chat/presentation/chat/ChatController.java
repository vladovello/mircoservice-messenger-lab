package com.messenger.chat.presentation.chat;

import com.messenger.chat.application.command.ChangeMultiChatCommand;
import com.messenger.chat.application.command.CreateMultiChatCommand;
import com.messenger.chat.application.command.handler.ChangeMultiChatCommandHandler;
import com.messenger.chat.application.command.handler.CreateMultiChatCommandHandler;
import com.messenger.chat.application.query.ChatInfoQuery;
import com.messenger.chat.application.query.ChatListQuery;
import com.messenger.chat.application.query.handler.ChatInfoQueryHandler;
import com.messenger.chat.application.query.handler.ChatListQueryHandler;
import com.messenger.chat.presentation.chat.dto.ChangeChatDto;
import com.messenger.chat.presentation.chat.dto.CreateChatDto;
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
@RequestMapping("api/chats/chat")
@RouterOperation
public class ChatController {
    private final CreateMultiChatCommandHandler createMultiChatCommandHandler;
    private final ChangeMultiChatCommandHandler changeMultiChatCommandHandler;
    private final ChatInfoQueryHandler chatInfoQueryHandler;
    private final ChatListQueryHandler chatListQueryHandler;

    public ChatController(
            CreateMultiChatCommandHandler createMultiChatCommandHandler,
            ChangeMultiChatCommandHandler changeMultiChatCommandHandler,
            ChatInfoQueryHandler chatInfoQueryHandler,
            ChatListQueryHandler chatListQueryHandler
    ) {
        this.createMultiChatCommandHandler = createMultiChatCommandHandler;
        this.changeMultiChatCommandHandler = changeMultiChatCommandHandler;
        this.chatInfoQueryHandler = chatInfoQueryHandler;
        this.chatListQueryHandler = chatListQueryHandler;
    }

    @PostMapping("create")
    public ResponseEntity<Object> createChat(
            @RequestBody @NonNull CreateChatDto createChatDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = createMultiChatCommandHandler.handle(new CreateMultiChatCommand(
                principal.getId(),
                createChatDto.getChatName(),
                createChatDto.getAvatarId(),
                createChatDto.getUsersList()
        ));

        return result.fold(
                chatCommandDto -> ResponseEntity.noContent().build(),
                e -> createBadRequestResponse(e.getMessage())
        );
    }

    @PatchMapping("change")
    public ResponseEntity<Object> changeChat(
            @NonNull ChangeChatDto changeChatDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = changeMultiChatCommandHandler.handle(new ChangeMultiChatCommand(
                principal.getId(),
                changeChatDto.getChatId(),
                changeChatDto.getChatName(),
                changeChatDto.getAvatarId(),
                changeChatDto.getUsersList()
        ));

        return result.fold(unit -> ResponseEntity.noContent().build(), e -> {
            if (e instanceof NotFoundException) return ResponseEntity.notFound().build();
            return createBadRequestResponse(e.getMessage());
        });
    }

    @GetMapping("{chatId}/info")
    public ResponseEntity<Object> getChatInfo(
            @PathVariable UUID chatId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var result = chatInfoQueryHandler.handle(new ChatInfoQuery(principal.getId(), chatId));

        return result.fold(ResponseEntity::ok, e -> {
            if (e instanceof NotFoundException) return ResponseEntity.notFound().build();
            return createBadRequestResponse(e.getMessage());
        });
    }

    @GetMapping("list")
    public ResponseEntity<Object> getChatList(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam String chatName,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var previewChatInfoListDto = chatListQueryHandler.handle(new ChatListQuery(principal.getId(), pageNumber, pageSize, chatName));

        return ResponseEntity.ok(previewChatInfoListDto);
    }

    private static @NonNull ResponseEntity<Object> createBadRequestResponse(String message) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                        HttpStatus.BAD_REQUEST,
                        message
                ));
    }
}
