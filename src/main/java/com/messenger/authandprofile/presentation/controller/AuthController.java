package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.messenger.authandprofile.application.auth.command.LoginUserCommand;
import com.messenger.authandprofile.application.auth.command.RefreshCommand;
import com.messenger.authandprofile.application.auth.command.RegisterUserCommand;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RouterOperation
public class AuthController {
    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("register")
    public ResponseEntity<UserWithTokenDto> registerUser(@NonNull @RequestBody RegisterUserCommand registerUserCommand) {
        var userWithTokenDto = registerUserCommand.execute(pipeline);
        return ResponseEntity.ok(userWithTokenDto);
    }

//    @PostMapping("login")
//    public ResponseEntity<UserWithTokenDto> login(@NonNull @RequestBody LoginUserCommand loginUserCommand) {
//        var userWithTokenDto = loginUserCommand.execute(pipeline);
//    }

    @PostMapping("refresh")
    public ResponseEntity<TokenPairDto> refresh(@NonNull @RequestBody RefreshCommand refreshCommand) {
        var eitherResult = refreshCommand.execute(pipeline);

        return eitherResult.fold(
                e -> ResponseEntity.notFound().build(),
                tokenPairDto -> tokenPairDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build())
        );
    }
}