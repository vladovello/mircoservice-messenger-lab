package com.messenger.authandprofile.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Principal {
    private final UUID id;
    private final String login;
}

