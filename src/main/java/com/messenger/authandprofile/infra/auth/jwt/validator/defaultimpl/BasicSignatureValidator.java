package com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import com.messenger.authandprofile.infra.auth.jwt.validator.SignatureValidator;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.NonNull;

public class BasicSignatureValidator implements SignatureValidator {

    @Override
    public boolean validate(String token, @NonNull JwtBearerTokenParameters jwtBearerTokenParameters) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtBearerTokenParameters.getVerificationKey()).build();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
