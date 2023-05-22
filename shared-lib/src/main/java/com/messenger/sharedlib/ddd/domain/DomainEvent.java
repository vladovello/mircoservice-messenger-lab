package com.messenger.sharedlib.ddd.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredOn();
}
