package com.messenger.authandprofile.infra.auth.service;

import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.infra.auth.jwt.factory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.jwt.factory.JwtClaimsFactory;
import com.messenger.authandprofile.infra.auth.persistence.RefreshTokenRepository;
import com.messenger.authandprofile.infra.auth.persistence.entity.RefreshTokenEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {
    private final JwtBearerTokenFactory jwtBearerTokenFactory;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenService(
            JwtBearerTokenFactory jwtBearerTokenFactory,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.jwtBearerTokenFactory = jwtBearerTokenFactory;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public TokenPair generateTokens(@NonNull User user) {
        var claims = JwtClaimsFactory.getUserClaims(user);
        var accessToken = generateAccessToken(claims);
        var refreshToken = generateRefreshToken(claims, user.getId());

        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public void invalidateRefreshToken(@NonNull String refreshToken) {
        var optionalToken = refreshTokenRepository.findById(refreshToken);

        if (optionalToken.isEmpty()) {
            throw new RefreshTokenNotFoundException(refreshToken);
        }

        var token = optionalToken.get();

        token.setInvalid();
        refreshTokenRepository.save(token);
    }

    @Override
    public void invalidateRefreshTokenFamily(@NonNull UUID userId) {
        var refreshTokenFamily = refreshTokenRepository.findAllByUserIdAndInvalid(userId, false);

        for (var refreshToken : refreshTokenFamily) {
            refreshToken.setInvalid();
        }

        refreshTokenRepository.saveAll(refreshTokenFamily);
    }

    @Override
    public boolean isRefreshTokenInvalidated(@NonNull String refreshToken) {
        return refreshTokenRepository.existsByTokenAndInvalid(refreshToken, true);
    }

    private String generateAccessToken(Map<String, ?> claims) {
        return jwtBearerTokenFactory.generateAccessToken(claims);
    }

    private String generateRefreshToken(Map<String, ?> claims, UUID subject) {
        var refreshToken = jwtBearerTokenFactory.generateRefreshToken(claims);
        refreshTokenRepository.save(new RefreshTokenEntity(refreshToken, subject));
        return refreshToken;
    }
}
