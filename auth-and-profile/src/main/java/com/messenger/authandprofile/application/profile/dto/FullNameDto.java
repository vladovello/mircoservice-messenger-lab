package com.messenger.authandprofile.application.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullNameDto {
    private String firstName;
    private String middleName;
    private String lastName;
}
