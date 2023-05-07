package com.messenger.chat.domain.chatparticipant.converter;

import com.messenger.chat.domain.chatparticipant.valueobject.ChatRoleName;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ChatRoleNameConverter implements AttributeConverter<ChatRoleName, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull ChatRoleName chatRoleName) {
        return chatRoleName.getName();
    }

    @Override
    public ChatRoleName convertToEntityAttribute(String s) {
        return ChatRoleName.create(s).getOrNull();
    }
}
