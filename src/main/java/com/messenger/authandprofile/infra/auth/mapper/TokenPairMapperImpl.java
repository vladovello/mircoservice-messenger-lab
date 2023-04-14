package com.messenger.authandprofile.infra.auth.mapper;

import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.mapper.TokenPairMapper;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TokenPairMapperImpl implements TokenPairMapper {
    @Override
    public TokenPair mapToTokenPair(@NonNull TokenPairDto tokenPairDto) {
        return new TokenPair(tokenPairDto.getAccessToken(), tokenPairDto.getRefreshToken());
    }

    @Override
    public TokenPairDto mapToTokenPairDto(@NonNull TokenPair tokenPair) {
        return new TokenPairDto(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }
}
