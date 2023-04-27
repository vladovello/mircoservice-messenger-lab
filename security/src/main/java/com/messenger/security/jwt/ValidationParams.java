package com.messenger.security.jwt;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidationParams {
    private String validationKey;
    private LocalDate expirationDate;
}
