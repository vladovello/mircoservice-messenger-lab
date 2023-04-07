package com.messenger.authandprofile.application.auth.service;

import com.messenger.authandprofile.domain.model.entity.User;

public interface TokenService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    void invalidateToken(User user);
}
