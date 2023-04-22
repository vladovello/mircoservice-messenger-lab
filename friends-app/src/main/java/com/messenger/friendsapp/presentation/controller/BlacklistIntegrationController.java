package com.messenger.friendsapp.presentation.controller;

import com.messenger.friendsapp.application.query.IsUserInBlacklistQuery;
import com.messenger.friendsapp.application.query.handler.IsUserInBlacklistQueryHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("integration/friends")
@RouterOperation
public class BlacklistIntegrationController {
    private final IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler;

    public BlacklistIntegrationController(IsUserInBlacklistQueryHandler isUserInBlacklistQueryHandler) {
        this.isUserInBlacklistQueryHandler = isUserInBlacklistQueryHandler;
    }

    @GetMapping("check")
    public Boolean isUserInBlacklist(@RequestParam UUID userId, @RequestParam UUID otherId) {
        return isUserInBlacklistQueryHandler.handle(new IsUserInBlacklistQuery(userId, otherId));
    }
}
