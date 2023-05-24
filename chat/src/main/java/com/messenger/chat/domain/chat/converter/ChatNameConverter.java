package com.messenger.chat.domain.chat.converter;

import com.messenger.chat.domain.chat.valueobject.ChatName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ChatNameConverter implements AttributeConverter<ChatName, String> {
    @Override
    public String convertToDatabaseColumn(ChatName chatName) {
        if (chatName == null) {
            return null;
        }

        return chatName.getName();
    }

    @Override
    public ChatName convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return ChatName.create(s).getOrNull();
    }
}
