package com.messenger.authandprofile.application.profile.query.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.FullNameDto;
import com.messenger.authandprofile.application.profile.query.GetFullNameByIdQuery;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class GetFullNameByIdQueryHandler implements Command.Handler<GetFullNameByIdQuery, Optional<FullNameDto>> {
    private final UserRepository userRepository;

    public GetFullNameByIdQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<FullNameDto> handle(@NonNull GetFullNameByIdQuery query) {
        var optionalUser = userRepository.findUserById(query.getUserId());

        if (optionalUser.isEmpty()) return Optional.empty();

        var fullName = optionalUser.get().getFullName();
        return Optional.of(new FullNameDto(fullName.getFirstName(), fullName.getMiddleName(), fullName.getLastName()));
    }
}
