package com.messenger.authandprofile.infra.auth.service;

import com.messenger.authandprofile.application.auth.exception.RefreshTokenNotFoundException;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.infra.auth.jwt.tokenfactory.JwtBearerTokenFactory;
import com.messenger.authandprofile.infra.auth.persistence.InvalidAccessTokenRepository;
import com.messenger.authandprofile.infra.auth.persistence.RefreshTokenRepository;
import com.messenger.authandprofile.infra.auth.persistence.entity.InvalidAccessToken;
import com.messenger.authandprofile.infra.auth.persistence.entity.RefreshToken;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {
    private final JwtBearerTokenFactory jwtBearerTokenFactory;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InvalidAccessTokenRepository invalidAccessTokenRepository;

    public JwtTokenService(JwtBearerTokenFactory jwtBearerTokenFactory,
            RefreshTokenRepository refreshTokenRepository,
            InvalidAccessTokenRepository invalidAccessTokenRepository) {
        this.jwtBearerTokenFactory = jwtBearerTokenFactory;
        this.refreshTokenRepository = refreshTokenRepository;
        this.invalidAccessTokenRepository = invalidAccessTokenRepository;
    }

    @Override
    public TokenPair generateTokens(@NonNull User user) {
        var claims = Map.of("id", user.getId());
        var accessToken = generateAccessToken(claims, user.getId());
        var refreshToken = generateRefreshToken(claims, user.getId());

        return new TokenPair(accessToken, refreshToken);
    }

    private String generateAccessToken(Map<String, ?> claims, UUID subject) {
        return jwtBearerTokenFactory.generateAccessToken(claims);
    }

    private String generateRefreshToken(Map<String, ?> claims, UUID subject) {
        var refreshToken = jwtBearerTokenFactory.generateRefreshToken(claims);
        refreshTokenRepository.save(new RefreshToken(refreshToken, subject));
        return refreshToken;
    }

    @Override
    public void invalidateAccessToken(@NonNull String accessToken) {
        invalidAccessTokenRepository.save(new InvalidAccessToken(accessToken));
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
        return refreshTokenRepository.existsByIdAndInvalid(refreshToken, true);
    }
}
