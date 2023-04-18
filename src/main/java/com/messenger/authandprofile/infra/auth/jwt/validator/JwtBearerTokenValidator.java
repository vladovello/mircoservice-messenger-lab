package com.messenger.authandprofile.infra.auth.jwt.validator;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtBearerTokenValidator implements TokenValidator {
    private final JwtBearerTokenParameters jwtBearerTokenParameters;

    public JwtBearerTokenValidator(@NonNull JwtBearerTokenParameters jwtBearerTokenOptions) {
        jwtBearerTokenParameters = jwtBearerTokenOptions;
    }

    public boolean validate(String token) {
        var result = jwtBearerTokenParameters.getSignatureValidator().validate(token, jwtBearerTokenParameters);

        if (result) {
            log.info("Successful verification of the token signature");
        } else {
            log.info("Token failed signature verification");
        }

        result = result && jwtBearerTokenParameters.getLifetimeValidator().validate(token, jwtBearerTokenParameters);

        if (result) {
            log.info("Successful verification of the token lifetime");
        } else {
            log.info("Token failed lifetime verification");
        }

        return result;
    }
}
