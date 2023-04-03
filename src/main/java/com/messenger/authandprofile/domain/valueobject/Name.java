package com.messenger.authandprofile.domain.valueobject;

import lombok.Value;

@Value
public class Name {
    String firstName;
    String middleName;
    String lastName;
}
