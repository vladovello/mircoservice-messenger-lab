package com.messenger.friendsapp.presentation.controller;

import com.messenger.friendsapp.application.command.AddToBlacklistCommand;
import com.messenger.friendsapp.application.command.DeleteFriendCommand;
import com.messenger.friendsapp.application.command.SynchronizeBlacklistDataCommand;
import com.messenger.friendsapp.application.command.handler.AddToBlacklistCommandHandler;
import com.messenger.friendsapp.application.command.handler.RemoveFromBlacklistCommandHandler;
import com.messenger.friendsapp.application.command.handler.SynchronizeBlacklistDataCommandHandler;
import com.messenger.friendsapp.application.query.IsUserInBlacklistQuery;
import com.messenger.friendsapp.application.query.handler.IsUserInBlacklistQueryHandler;
import com.messenger.friendsapp.presentation.controller.dto.AddToBlacklistDto;
import com.messenger.friendsapp.presentation.controller.dto.IsUserInBlackListDto;
import com.messenger.security.jwt.PayloadPrincipal;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/friends/blacklist")
@RouterOperation
public class BlacklistController {
    private final RemoveFromBlacklistCommandHandler removeFromBlacklistCommandHandler;
    private final AddToBlacklistCommandHandler addToBlacklistCommandHandler;
    private final IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler;
    private final SynchronizeBlacklistDataCommandHandler synchronizeBlacklistDataCommandHandler;

    public BlacklistController(
            RemoveFromBlacklistCommandHandler removeFromBlacklistCommandHandler,
            AddToBlacklistCommandHandler addToBlacklistCommandHandler,
            IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler,
            SynchronizeBlacklistDataCommandHandler synchronizeBlacklistDataCommandHandler
    ) {
        this.removeFromBlacklistCommandHandler = removeFromBlacklistCommandHandler;
        this.addToBlacklistCommandHandler = addToBlacklistCommandHandler;
        this.isUserInBlacklistQueryHandler = isUserInBlacklistQueryHandler;
        this.synchronizeBlacklistDataCommandHandler = synchronizeBlacklistDataCommandHandler;
    }

    @DeleteMapping("delete/{blacklistedId:UUID}")
    public ResponseEntity<Void> removeFromBlacklist(
            @PathVariable UUID blacklistedId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        removeFromBlacklistCommandHandler.handle(new DeleteFriendCommand(principal.getId(), blacklistedId));
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
            UUID addresseeId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var isBlacklisted = isUserInBlacklistQueryHandler.handle(new IsUserInBlacklistQuery(
                principal.getId(),
                addresseeId
        ));
        return ResponseEntity.ok(new IsUserInBlackListDto(isBlacklisted));
    }

    @PatchMapping("synchronize")
    public ResponseEntity<Void> synchronizeFullName(@RequestParam UUID friendId) {
        var result = synchronizeBlacklistDataCommandHandler.handle(new SynchronizeBlacklistDataCommand(friendId));

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
