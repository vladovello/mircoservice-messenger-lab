package com.messenger.chat.domain.valueobject;

import lombok.Value;

@Value
public class FullName {
    String firstName;
    String middleName;
    String lastName;
}
