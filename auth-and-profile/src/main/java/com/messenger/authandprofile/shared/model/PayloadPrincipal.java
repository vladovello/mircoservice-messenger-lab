package com.messenger.authandprofile.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PayloadPrincipal {
    private final UUID id;
    private final String login;
}

