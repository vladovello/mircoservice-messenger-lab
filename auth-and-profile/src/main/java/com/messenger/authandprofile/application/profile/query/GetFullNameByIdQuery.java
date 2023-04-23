package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.FullNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetFullNameByIdQuery implements Command<Optional<FullNameDto>> {
    UUID userId;
}
