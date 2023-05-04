package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.SynchronizeFriendDataCommand;
import com.messenger.friendsapp.application.repository.ProfileRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SynchronizeFriendDataCommandHandler {
    private final FriendshipRepository friendshipRepository;
    private final ProfileRepository profileRepository;

    public SynchronizeFriendDataCommandHandler(
            FriendshipRepository friendshipRepository,
            ProfileRepository profileRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.profileRepository = profileRepository;
    }

    public Optional<Unit> handle(@NonNull SynchronizeFriendDataCommand command) {
        var optionalFullName = profileRepository.getUsernameById(command.getFriendId());
        if (optionalFullName.isEmpty()) {
            return Optional.empty();
        }

        var fullNameDto = optionalFullName.get();

        friendshipRepository.updateAllAddresseeIdFullName(
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
