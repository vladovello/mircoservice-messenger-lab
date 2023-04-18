package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import com.messenger.authandprofile.shared.model.Principal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetOtherProfileInfoQuery implements Command<Optional<ProfileDto>> {
    private UUID otherId;
    private Principal principal;
}
