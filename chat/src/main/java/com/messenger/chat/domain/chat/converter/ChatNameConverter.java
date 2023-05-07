package com.messenger.chat.domain.chat.converter;

import com.messenger.chat.domain.chat.valueobject.ChatName;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ChatNameConverter implements AttributeConverter<ChatName, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull ChatName chatName) {
        return chatName.getName();
    }

    @Override
    public ChatName convertToEntityAttribute(@NonNull String s) {
        return ChatName.create(s).getOrNull();
    }
}
