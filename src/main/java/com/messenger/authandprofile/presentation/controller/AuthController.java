package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Voidy;
import com.messenger.authandprofile.application.auth.command.LoginUserCommand;
import com.messenger.authandprofile.application.auth.command.LogoutUserCommand;
import com.messenger.authandprofile.application.auth.command.RefreshCommand;
import com.messenger.authandprofile.application.auth.command.RegisterUserCommand;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.exception.PasswordsDoNotMatchException;
import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.exception.RefreshTokenReuseException;
import com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import io.vavr.API;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RestController
@RequestMapping("api/auth")
@RouterOperation
public class AuthController {
    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("register")
    public ResponseEntity<UserWithTokenDto> registerUser(@NonNull @RequestBody RegisterUserCommand registerUserCommand) {
        var either = registerUserCommand.execute(pipeline);

        return either.fold(
                e -> API.Match(e).of(
                        Case(
                                $(instanceOf(UserAlreadyExistsException.class)),
                                ResponseEntity.status(HttpStatus.CONFLICT).build()
                        )
                ),
                ResponseEntity::ok
        );
    }

    @PostMapping("login")
    public ResponseEntity<UserWithTokenDto> login(@NonNull @RequestBody LoginUserCommand loginUserCommand) {
        var either = loginUserCommand.execute(pipeline);

        return either.fold(
                e -> API.Match(e).of(
                        Case(
                                $(instanceOf(PasswordsDoNotMatchException.class)),
                                ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                        ),
                        Case($(instanceOf(UserNotFoundException.class)), ResponseEntity.notFound().build())
                ),
                ResponseEntity::ok
        );
    }

    @PostMapping("logout")
    public ResponseEntity<Voidy> logout(@NonNull @RequestBody LogoutUserCommand logoutUserCommand) {
        var either = logoutUserCommand.execute(pipeline);

        return either.fold(
                e -> API.Match(e).of(
                        Case($(instanceOf(RefreshTokenNotFoundException.class)), ResponseEntity.notFound().build())
                ),
                ResponseEntity::ok
        );
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenPairDto> refresh(@NonNull @RequestBody RefreshCommand refreshCommand) {
        var either = refreshCommand.execute(pipeline);

        return either.fold(
                e -> API.Match(e).of(
                        Case(
                                $(instanceOf(RefreshTokenReuseException.class)),
                                ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                        ),
                        Case($(instanceOf(UserNotFoundException.class)), ResponseEntity.notFound().build())
                ),
                ResponseEntity::ok
        );
    }
}