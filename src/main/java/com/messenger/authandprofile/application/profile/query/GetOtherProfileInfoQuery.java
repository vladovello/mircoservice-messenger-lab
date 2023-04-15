package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class GetOtherProfileInfoQuery implements Command<Optional<UserDto>> {
    private UUID selfId;
    private UUID otherId;
}
