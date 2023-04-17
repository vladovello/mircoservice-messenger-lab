package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.messenger.authandprofile.application.auth.command.RegisterUserCommand;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.infra.auth.persistence.RefreshTokenRepository;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RouterOperation
public class AuthController {
    private final Pipeline pipeline;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(Pipeline pipeline, RefreshTokenRepository refreshTokenRepository) {
        this.pipeline = pipeline;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("register")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<UserWithTokenDto> registerUser(@NonNull @RequestBody RegisterUserCommand registerUserCommand) {
        var userWithTokenDto = registerUserCommand.execute(pipeline);
        return ResponseEntity.ok(userWithTokenDto);
    }

    @GetMapping("refresh")
    public ResponseEntity<String> getRefreshToken(String refreshToken) {
        var token = refreshTokenRepository.findById(refreshToken);

        return token.map(refreshTokenEntity -> ResponseEntity.ok(refreshTokenEntity.getToken()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}