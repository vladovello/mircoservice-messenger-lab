package com.messenger.chat.domain.user.converter;

import com.messenger.chat.domain.user.valueobject.FullName;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FullNameConverter implements AttributeConverter<FullName, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull FullName fullName) {
        return fullName.getValue();
    }

    @Override
    public FullName convertToEntityAttribute(@NonNull String s) {
        var split = s.split("\\s");
        if (split.length == 2) {
            return FullName.create(split[0], null, split[1]).getOrNull();
        }

        return FullName.create(split[0], split[1], split[2]).getOrNull();
    }
}
