package com.messenger.authandprofile.application.auth.model;

import lombok.Value;

@Value
public class TokenPair {
    // TODO: 18.04.2023 add AccessToken and RefreshToken classes
    String accessToken;
    String refreshToken;
}
