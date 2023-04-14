package com.messenger.authandprofile.infra.auth.jwt.validator;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import lombok.NonNull;

public final class TokenValidator {
    private TokenValidator() {
    }

    // TODO: 14.04.2023 logging
    public static boolean validate(String token, @NonNull JwtBearerTokenParameters jwtBearerTokenParameters) {
        var result = jwtBearerTokenParameters.getSignatureValidator().validate(token, jwtBearerTokenParameters);
        result = result && jwtBearerTokenParameters.getLifetimeValidator().validate(token, jwtBearerTokenParameters);

        return result;
    }
}
