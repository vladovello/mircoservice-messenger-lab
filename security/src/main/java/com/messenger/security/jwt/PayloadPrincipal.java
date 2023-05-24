package com.messenger.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PayloadPrincipal {
    private final UUID id;
}

