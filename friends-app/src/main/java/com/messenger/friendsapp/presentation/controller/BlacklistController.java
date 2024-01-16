package com.messenger.friendsapp.presentation.controller;

import com.messenger.friendsapp.application.command.AddToBlacklistCommand;
import com.messenger.friendsapp.application.command.RemoveFromBlacklistCommand;
import com.messenger.friendsapp.application.command.handler.AddToBlacklistCommandHandler;
import com.messenger.friendsapp.application.command.handler.RemoveFromBlacklistCommandHandler;
import com.messenger.friendsapp.application.query.IsUserInBlacklistQuery;
import com.messenger.friendsapp.application.query.handler.IsUserInBlacklistQueryHandler;
import com.messenger.friendsapp.presentation.controller.dto.AddToBlacklistDto;
import com.messenger.friendsapp.presentation.controller.dto.IsUserInBlackListDto;
import com.messenger.security.jwt.PayloadPrincipal;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/friends/blacklist")
@RouterOperation
@Validated
public class BlacklistController {
    private final RemoveFromBlacklistCommandHandler removeFromBlacklistCommandHandler;
    private final AddToBlacklistCommandHandler addToBlacklistCommandHandler;
    private final IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler;

    public BlacklistController(
            RemoveFromBlacklistCommandHandler removeFromBlacklistCommandHandler,
            AddToBlacklistCommandHandler addToBlacklistCommandHandler,
            IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler
    ) {
        this.removeFromBlacklistCommandHandler = removeFromBlacklistCommandHandler;
        this.addToBlacklistCommandHandler = addToBlacklistCommandHandler;
        this.isUserInBlacklistQueryHandler = isUserInBlacklistQueryHandler;
    }

    @DeleteMapping("delete/{blacklistedId}")
    public ResponseEntity<Void> removeFromBlacklist(
            @PathVariable UUID blacklistedId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        removeFromBlacklistCommandHandler.handle(new RemoveFromBlacklistCommand(principal.getId(), blacklistedId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("add")
    public ResponseEntity<Void> addToBlacklist(
            @RequestBody @NonNull AddToBlacklistDto addToBlacklistDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        addToBlacklistCommandHandler.handle(new AddToBlacklistCommand(
                principal.getId(),
                addToBlacklistDto.getAddresseeId(),
                addToBlacklistDto.getFirstName(),
                addToBlacklistDto.getMiddleName(),
                addToBlacklistDto.getLastName()
        ));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("check")
    public ResponseEntity<IsUserInBlackListDto> isUserInBlacklist(
            @Valid @RequestParam UUID addresseeId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var isBlacklisted = isUserInBlacklistQueryHandler.handle(new IsUserInBlacklistQuery(
                principal.getId(),
                addresseeId
        ));
        return ResponseEntity.ok(new IsUserInBlackListDto(isBlacklisted));
    }
}
