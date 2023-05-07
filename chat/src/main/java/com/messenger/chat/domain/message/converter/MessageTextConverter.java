package com.messenger.chat.domain.message.converter;

import com.messenger.chat.domain.message.valueobject.MessageText;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MessageTextConverter implements AttributeConverter<MessageText, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull MessageText messageText) {
        return messageText.getText();
    }

    @Override
    public MessageText convertToEntityAttribute(String s) {
        return MessageText.create(s).getOrNull();
    }
}
