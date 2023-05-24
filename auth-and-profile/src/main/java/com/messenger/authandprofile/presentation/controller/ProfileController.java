package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.messenger.authandprofile.application.auth.command.LogoutUserCommand;
import com.messenger.authandprofile.application.profile.command.EditUserProfileCommand;
import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import com.messenger.authandprofile.application.profile.exception.ConstraintViolationException;
import com.messenger.authandprofile.application.profile.exception.IntervalException;
import com.messenger.authandprofile.application.profile.model.UserListDto;
import com.messenger.authandprofile.application.profile.query.GetOtherProfileInfoQuery;
import com.messenger.authandprofile.application.profile.query.GetSelfProfileInfoQuery;
import com.messenger.authandprofile.application.profile.query.UserListQuery;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.presentation.CQSLoggerProbe;
import com.messenger.security.jwt.PayloadPrincipal;
import io.vavr.API;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RestController
@RequestMapping("api/users/profile")
@RouterOperation
public class ProfileController {
    private final Pipeline pipeline;

    public ProfileController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("self")
    public ResponseEntity<ProfileDto> getSelf(@NonNull Authentication authentication) {
        var payloadPrincipal = (PayloadPrincipal) authentication.getPrincipal();

        var query = new GetSelfProfileInfoQuery(payloadPrincipal);

        CQSLoggerProbe.execStarted(LogoutUserCommand.class);

        var profileDto = query.execute(pipeline);

        CQSLoggerProbe.execFinished(GetSelfProfileInfoQuery.class);

        return profileDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("other")
    public ResponseEntity<ProfileDto> getOther(
            @RequestParam @NonNull UUID requestedId,
            @NonNull Authentication authentication
    ) {
        var payloadPrincipal = (PayloadPrincipal) authentication.getPrincipal();

        CQSLoggerProbe.execStarted(LogoutUserCommand.class);

        var query = new GetOtherProfileInfoQuery(requestedId, payloadPrincipal);

        CQSLoggerProbe.execFinished(GetOtherProfileInfoQuery.class);

        var otherProfile = query.execute(pipeline);
        return otherProfile.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("all")
    public ResponseEntity<UserListDto> getAllSorted(@NonNull @RequestBody UserListQuery userListQuery) {
        CQSLoggerProbe.execStarted(LogoutUserCommand.class);

        userListQuery.setPageNumber(userListQuery.getPageNumber());
        userListQuery.setPageSize(userListQuery.getPageSize());

        var either = userListQuery.execute(pipeline);

        CQSLoggerProbe.execFinished(UserListQuery.class);

        return either.fold(e -> API.Match(e).of(
                Case(
                        $(instanceOf(ConstraintViolationException.class)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                        }
                ),
                Case(
                        $(instanceOf(IntervalException.class)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                        }
                )
        ), ResponseEntity::ok);
    }

    @PutMapping("edit")
    public ResponseEntity<UserDto> editUser(@NonNull @RequestBody EditUserProfileCommand editUserProfileCommand) {
        CQSLoggerProbe.execStarted(LogoutUserCommand.class);

        var either = editUserProfileCommand.execute(pipeline);

        CQSLoggerProbe.execFinished(EditUserProfileCommand.class);

        return either.fold(e -> API.Match(e).of(
                Case($(instanceOf(UserNotFoundException.class)), ResponseEntity.notFound().build())
        ), ResponseEntity::ok);
    }
}
