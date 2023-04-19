package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.ProfileDto;
import com.messenger.authandprofile.shared.model.PayloadPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

/**
 * @apiNote @see block shows the exceptions may be thrown by this query
 * @see com.messenger.authandprofile.application.profile.exception.ForbiddenException
 */
@Data
@AllArgsConstructor
public class GetSelfProfileInfoQuery implements Command<Optional<ProfileDto>> {
    private PayloadPrincipal payloadPrincipal;
}
