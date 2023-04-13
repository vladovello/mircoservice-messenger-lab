package com.messenger.authandprofile.application.auth.model;

import lombok.Value;

@Value
public class TokenPair {
    String accessToken;
    String refreshToken;
}
