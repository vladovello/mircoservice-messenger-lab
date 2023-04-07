package com.messenger.authandprofile.domain.model.valueobject;

import lombok.Value;

@Value
public class FullName {
    String firstName;
    String middleName;
    String lastName;
}
