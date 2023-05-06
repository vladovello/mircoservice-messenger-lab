package com.messenger.chat.domain.user.valueobject;

import lombok.Value;

@Value
public class FullName {
    String firstName;
    String middleName;
    String lastName;
}
