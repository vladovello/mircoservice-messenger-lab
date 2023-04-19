package com.messenger.authandprofile.infra.spring.security;

import com.messenger.authandprofile.shared.model.PayloadPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PayloadAuthentication extends AbstractAuthenticationToken {
    public PayloadAuthentication(PayloadPrincipal jwtUserData) {
        super(null);
        this.setDetails(jwtUserData);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }

}

