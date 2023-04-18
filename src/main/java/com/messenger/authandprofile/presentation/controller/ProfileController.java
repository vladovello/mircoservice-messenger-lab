package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.messenger.authandprofile.application.profile.command.EditUserProfileCommand;
import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.model.UserListDto;
import com.messenger.authandprofile.application.profile.query.GetOtherProfileInfoQuery;
import com.messenger.authandprofile.application.profile.query.GetSelfProfileInfoQuery;
import com.messenger.authandprofile.application.profile.query.UserListQuery;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.shared.model.Principal;
import io.vavr.API;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RestController
@RequestMapping("api/profile")
@RouterOperation
public class ProfileController {
    private final Pipeline pipeline;

    public ProfileController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    // INFO: Maybe principal should be extracted from header with Spring Security filters
    @GetMapping("self")
    public ResponseEntity<ProfileDto> getSelf(
            @NonNull Principal principal
    ) {
        var query = new GetSelfProfileInfoQuery(principal);
        var profileDto = query.execute(pipeline);
        return profileDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("other")
    public ResponseEntity<ProfileDto> getOther(
            @RequestParam @NonNull UUID requestedId,
            @NonNull Principal principal
    ) {
        var query = new GetOtherProfileInfoQuery(requestedId, principal);
        var otherProfile = query.execute(pipeline);
        return otherProfile.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("all")
    public ResponseEntity<UserListDto> getAllSorted(@NonNull @RequestBody UserListQuery userListQuery) {
        var userListDto = userListQuery.execute(pipeline);
        return ResponseEntity.ok(userListDto);
    }

    @PutMapping("edit")
    public ResponseEntity<UserDto> editUser(@NonNull @RequestBody EditUserProfileCommand editUserProfileCommand) {
        var either = editUserProfileCommand.execute(pipeline);
        return either.fold(e -> API.Match(e).of(
                Case($(instanceOf(UserNotFoundException.class)), ResponseEntity.notFound().build())
        ), ResponseEntity::ok);
    }
}
