package com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;
import com.messenger.authandprofile.infra.auth.jwt.validator.LifetimeValidator;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class BasicLifetimeValidator implements LifetimeValidator {
    @Override
    public boolean validate(String token, JwtBearerTokenParameters jwtBearerTokenParameters) {
        try {
            Jwts.parserBuilder()
                    .requireExpiration(jwtBearerTokenParameters.getExpirationDate())
                    .setSigningKey(jwtBearerTokenParameters.getSigningKey())
                    .build();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
