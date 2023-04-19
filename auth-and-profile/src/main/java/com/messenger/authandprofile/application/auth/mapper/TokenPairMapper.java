package com.messenger.authandprofile.application.auth.mapper;

import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.model.TokenPair;
import lombok.NonNull;

public interface TokenPairMapper {
    TokenPair mapToTokenPair(@NonNull TokenPairDto tokenPairDto);

    TokenPairDto mapToTokenPairDto(@NonNull TokenPair tokenPair);
}
