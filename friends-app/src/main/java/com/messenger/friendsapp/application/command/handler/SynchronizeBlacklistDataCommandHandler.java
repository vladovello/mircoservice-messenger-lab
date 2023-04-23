package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.SynchronizeBlacklistDataCommand;
import com.messenger.friendsapp.application.repository.ProfileRepository;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.shared.Unit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SynchronizeBlacklistDataCommandHandler {
    private final BlacklistRepository blacklistRepository;
    private final ProfileRepository profileRepository;

    public SynchronizeBlacklistDataCommandHandler(
            BlacklistRepository blacklistRepository,
            ProfileRepository profileRepository
    ) {
        this.blacklistRepository = blacklistRepository;
        this.profileRepository = profileRepository;
    }

    public Optional<Unit> handle(@NonNull SynchronizeBlacklistDataCommand command) {
        var optionalFullName = profileRepository.getUsernameById(command.getFriendId());
        if (optionalFullName.isEmpty()) {
            return Optional.empty();
        }

        var fullNameDto = optionalFullName.get();

        blacklistRepository.updateAllAddresseeIdFullName(
                command.getFriendId(),
                new FullName(
                        fullNameDto.getFirstName(),
                        fullNameDto.getMiddleName(),
                        fullNameDto.getLastName()
                )
        );

        return Optional.of(Unit.INSTANCE);
    }
}
