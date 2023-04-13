package com.messenger.authandprofile.infra.auth.jwt.validator;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import lombok.NonNull;

public final class TokenValidator {
    private TokenValidator() {
    }

    // TODO: 13.04.2023 add proper exceptions
    public static boolean validate(String token, @NonNull JwtBearerTokenParameters jwtBearerTokenParameters) {
        var isSigValid = jwtBearerTokenParameters.getSignatureValidator().validate(token, jwtBearerTokenParameters);
        if (!isSigValid) {
            throw new RuntimeException();
        }

        var isLifetimeValid = jwtBearerTokenParameters.getLifetimeValidator().validate(token, jwtBearerTokenParameters);
        if (!isLifetimeValid) {
            throw new RuntimeException();
        }

        return true;
    }
}
