package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.ProfileChangedEventCommand;
import com.messenger.chat.domain.user.ChatUser;
import com.messenger.chat.domain.user.repository.UserRepository;
import com.messenger.chat.domain.user.valueobject.FullName;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileChangedEventCommandHandler implements CommandHandler<ProfileChangedEventCommand, Result<Unit>> {
    private final UserRepository userRepository;

    public ProfileChangedEventCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<Unit> handle(ProfileChangedEventCommand command) {
        log.info(String.format("ProfileChangedEventCommand received: %s", command));

        var fullName = FullName.create(command.getFirstName(), command.getMiddleName(), command.getLastName());

        if (fullName.isFailure()) {
            return Result.failure(fullName.exceptionOrNull());
        }

        var chatUser = ChatUser.reconstruct(
                command.getUserId(),
                fullName.getOrNull(),
                command.getAvatarId()
        );

        userRepository.save(chatUser);

        return Result.success();
    }
}
