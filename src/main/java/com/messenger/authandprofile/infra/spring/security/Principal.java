package com.messenger.authandprofile.infra.spring.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Principal {
    private final UUID id;

    private final String login;
}

