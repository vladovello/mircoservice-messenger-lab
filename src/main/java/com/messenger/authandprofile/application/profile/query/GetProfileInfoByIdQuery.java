package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import lombok.Value;

import java.util.UUID;

@Value
public class GetProfileInfoByIdQuery implements Command<UserDto> {
    UUID id;
}
