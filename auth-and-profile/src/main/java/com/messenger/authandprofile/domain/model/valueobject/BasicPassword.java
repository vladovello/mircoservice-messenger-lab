package com.messenger.authandprofile.domain.model.valueobject;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
public class BasicPassword implements Password {
    @Getter
    @NonNull
    String value;

    public BasicPassword(@NonNull String value) {
        this.value = value;
    }
}
