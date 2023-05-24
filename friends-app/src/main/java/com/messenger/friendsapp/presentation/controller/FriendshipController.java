package com.messenger.friendsapp.presentation.controller;

import com.messenger.friendsapp.application.command.AddFriendCommand;
import com.messenger.friendsapp.application.command.DeleteFriendCommand;
import com.messenger.friendsapp.application.command.handler.AddFriendCommandHandler;
import com.messenger.friendsapp.application.command.handler.DeleteFriendCommandHandler;
import com.messenger.friendsapp.application.dto.FriendDto;
import com.messenger.friendsapp.application.dto.FriendsListDto;
import com.messenger.friendsapp.application.query.FriendsListQuery;
import com.messenger.friendsapp.application.query.GetFriendInfoQuery;
import com.messenger.friendsapp.application.query.SearchFriendsByParamsQuery;
import com.messenger.friendsapp.application.query.handler.FriendsListQueryHandler;
import com.messenger.friendsapp.application.query.handler.GetFriendInfoQueryHandler;
import com.messenger.friendsapp.application.query.handler.SearchFriendsByParamsQueryHandler;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;
import com.messenger.friendsapp.presentation.controller.dto.AddFriendDto;
import com.messenger.friendsapp.presentation.controller.dto.FriendsListRequestDto;
import com.messenger.security.jwt.PayloadPrincipal;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/friends/friendship")
@RouterOperation
public class FriendshipController {
    private final SearchFriendsByParamsQueryHandler searchFriendsByParamsQueryHandler;
    private final GetFriendInfoQueryHandler getFriendInfoQueryHandler;
    private final FriendsListQueryHandler friendsListQueryHandler;
    private final AddFriendCommandHandler addFriendCommandHandler;
    private final DeleteFriendCommandHandler deleteFriendCommandHandler;

    public FriendshipController(
            SearchFriendsByParamsQueryHandler searchFriendsByParamsQueryHandler,
            GetFriendInfoQueryHandler getFriendInfoQueryHandler,
            FriendsListQueryHandler friendsListQueryHandler,
            AddFriendCommandHandler addFriendCommandHandler,
            DeleteFriendCommandHandler deleteFriendCommandHandler
    ) {
        this.searchFriendsByParamsQueryHandler = searchFriendsByParamsQueryHandler;
        this.getFriendInfoQueryHandler = getFriendInfoQueryHandler;
        this.friendsListQueryHandler = friendsListQueryHandler;
        this.addFriendCommandHandler = addFriendCommandHandler;
        this.deleteFriendCommandHandler = deleteFriendCommandHandler;
    }

    @PostMapping("search")
    public ResponseEntity<FriendsListDto> getFriendsByParams(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(required = false) DiscreteParam<String> fullName,
            @RequestParam(required = false) IntervalParam<LocalDate> additionDate,
            @RequestParam(required = false) DiscreteParam<UUID> friendId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var friendsListDto = searchFriendsByParamsQueryHandler.handle(new SearchFriendsByParamsQuery(
                pageNumber,
                pageSize,
                principal.getId(),
                fullName,
                additionDate,
                friendId
        ));
        return ResponseEntity.ok(friendsListDto);
    }

    @GetMapping("{friendId}")
    public ResponseEntity<FriendDto> getFriend(
            @PathVariable UUID friendId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var optionalFriendDto = getFriendInfoQueryHandler.handle(new GetFriendInfoQuery(principal.getId(), friendId));

        return optionalFriendDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("all")
    public ResponseEntity<List<FriendDto>> getFriendsList(
            @RequestBody @NonNull FriendsListRequestDto friendsListRequestDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var friendDtoList = friendsListQueryHandler.handle(new FriendsListQuery(friendsListRequestDto.getPageSize(),
                friendsListRequestDto.getPageNumber(), principal.getId(), friendsListRequestDto.getFullName()
        ));

        return ResponseEntity.ok(friendDtoList);
    }

    @PostMapping("add")
    public ResponseEntity<Void> addFriend(
            @RequestBody @NonNull AddFriendDto addFriendDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        try {
            addFriendCommandHandler.handle(new AddFriendCommand(
                    principal.getId(),
                    addFriendDto.getAddresseeId(),
                    addFriendDto.getFirstName(),
                    addFriendDto.getMiddleName(),
                    addFriendDto.getLastName()
            ));

            return ResponseEntity.noContent().build();
        } catch (UserIsBlockedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("delete/{friendId}")
    public ResponseEntity<Void> deleteFriend(
            @PathVariable @NonNull UUID friendId,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        deleteFriendCommandHandler.handle(new DeleteFriendCommand(principal.getId(), friendId));
        return ResponseEntity.noContent().build();
    }
}
