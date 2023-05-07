package com.messenger.chat.domain.chatparticipant.converter;

import com.messenger.chat.domain.chatparticipant.Permission;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Converter
public class PermissionConverter implements AttributeConverter<Set<Permission>, List<Integer>> {
    @Override
    public List<Integer> convertToDatabaseColumn(@NonNull Set<Permission> permissions) {
        List<Integer> dbPermissions = new ArrayList<>();
        permissions.forEach(permission -> dbPermissions.add(permission.getCode()));
        return dbPermissions;
    }

    @Override
    public Set<Permission> convertToEntityAttribute(List<Integer> integers) {
        return Permission.of(integers);
    }
}
