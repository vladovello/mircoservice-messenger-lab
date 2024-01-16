package com.messenger.sharedlib.domain.ddd;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDomainEvent implements DomainEvent {
    private LocalDateTime occurredOn;

    public BaseDomainEvent() {
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
