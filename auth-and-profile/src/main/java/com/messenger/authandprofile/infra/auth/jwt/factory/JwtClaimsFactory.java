package com.messenger.authandprofile.infra.auth.jwt.factory;

import com.messenger.authandprofile.domain.model.entity.User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public final class JwtClaimsFactory {
    private JwtClaimsFactory() {
    }

    // INFO: adding claims this way isn't convenient and may lead to inconsistency between Principal and claims.
    //  The solution will be to construct claims according to some scheme or another way of coupling Principal and claims.
    public static @NonNull Map<String, Object> getUserClaims(@NonNull User user) {
        var claims = new HashMap<String, Object>();
        claims.put(Claims.SUBJECT, user.getId());
        claims.put("log", user.getLogin().getValue());
        return claims;
    }
}
