package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.UserCreatedEventCommand;
import com.messenger.chat.domain.user.ChatUser;
import com.messenger.chat.domain.user.repository.UserRepository;
import com.messenger.chat.domain.user.valueobject.FullName;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCreatedEventCommandHandler implements CommandHandler<UserCreatedEventCommand, Result<Unit>> {
    private final UserRepository userRepository;

    public UserCreatedEventCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<Unit> handle(@NonNull UserCreatedEventCommand command) {
        log.info(String.format("UserCreatedEventCommand received: %s", command));

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
