package com.messenger.chat.domain.user.converter;

import com.messenger.chat.domain.user.valueobject.FullName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FullNameConverter implements AttributeConverter<FullName, String> {
    @Override
    public String convertToDatabaseColumn(FullName fullName) {
        return fullName.getFirstName();
    }

    @Override
    public FullName convertToEntityAttribute(String s) {
        return null;
    }
}
