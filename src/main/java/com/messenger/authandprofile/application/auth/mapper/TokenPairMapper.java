package com.messenger.authandprofile.application.auth.mapper;

import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.model.TokenPair;

public interface TokenPairMapper {
    TokenPair mapToTokenPair(TokenPairDto tokenPairDto);

    TokenPairDto mapToTokenPairDto(TokenPair tokenPair);
}
