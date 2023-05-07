package com.messenger.chat.domain.identity.converter;

import com.messenger.chat.domain.identity.ChatId;
import com.messenger.sharedlib.ddd.domain.UuidIdentity;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter
public class UuidConverter implements AttributeConverter<UuidIdentity, UUID> {
    @Override
    public UUID convertToDatabaseColumn(@NonNull UuidIdentity uuidIdentity) {
        return uuidIdentity.getId();
    }

    @Override
    public ChatId convertToEntityAttribute(UUID uuid) {
        return new ChatId(uuid);
    }
}
