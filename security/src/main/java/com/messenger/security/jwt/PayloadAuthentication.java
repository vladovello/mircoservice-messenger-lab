package com.messenger.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PayloadAuthentication extends AbstractAuthenticationToken {
    public PayloadAuthentication(PayloadPrincipal payloadPrincipal) {
        super(null);
        this.setDetails(payloadPrincipal);
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

