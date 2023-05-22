package com.messenger.notification.helper;

import lombok.NonNull;

public class FullNameHelper {
    private FullNameHelper() {
    }

    public static @NonNull String concatFullName(
            @NonNull String firstName,
            @NonNull String lastName,
            String middleName
    ) {
        return middleName == null ? firstName + " " + lastName : firstName + " " + middleName + " " + lastName;
    }
}
