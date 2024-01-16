package com.messenger.sharedlib.domain.ddd;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredOn();
}
