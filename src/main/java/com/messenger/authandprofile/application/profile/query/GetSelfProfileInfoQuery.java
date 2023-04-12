package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import lombok.Data;

import java.util.UUID;

@Data
public class GetSelfProfileInfoQuery implements Command<UserDto> {
    private UUID id;
}
