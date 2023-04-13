package com.messenger.authandprofile.application.auth.service;

import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.domain.model.entity.User;
import lombok.NonNull;

import java.util.UUID;

public interface TokenService {
    TokenPair generateTokens(@NonNull User user);

    void invalidateAccessToken(@NonNull String accessToken);

    void invalidateRefreshToken(@NonNull String refreshToken) throws RefreshTokenNotFoundException;

    void invalidateRefreshTokenFamily(@NonNull UUID userId);

    boolean isRefreshTokenInvalidated(@NonNull String refreshToken);
}
