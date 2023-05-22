package com.messenger.authandprofile.domain.model.valueobject;

import lombok.NonNull;
import lombok.Value;

@Value
public class FullName {
    @NonNull String firstName;
    String middleName;
    @NonNull String lastName;

    public String getFull() {
        return middleName == null ? String.join(" ", firstName, lastName) : String.join(
                " ",
                firstName,
                lastName,
                middleName
        );
    }
}
