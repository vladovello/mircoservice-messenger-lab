package com.messenger.authandprofile.application.profile.model;

import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import lombok.Value;

import java.util.List;

@Value
public class UserListDto {
    int pageSize;
    int pageNumber;
    List<ProfileDto> users;
}
