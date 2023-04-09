package com.messenger.authandprofile.application.auth.service;

import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.domain.model.entity.User;

import java.util.UUID;

public interface TokenService {
    TokenPair generateTokens(User user);
    void invalidateAccessToken(String accessToken);
    void invalidateRefreshToken(String refreshToken);
    void invalidateRefreshTokenFamily(UUID userId);
    boolean wasRefreshTokenUsed(String refreshToken);
}
