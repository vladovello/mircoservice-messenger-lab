package com.messenger.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ValidationParams {
    private String validationKey;
    private LocalTime expirationTime;
}
