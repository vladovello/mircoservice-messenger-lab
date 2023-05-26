package com.messenger.chat.application.command.handler;

import com.messenger.chat.application.command.CommandHandler;
import com.messenger.chat.application.command.CreateMultiChatCommand;
import com.messenger.chat.application.command.dto.ChatCommandDto;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.valueobject.ChatName;
import com.messenger.sharedlib.util.Result;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CreateMultiChatCommandHandler implements CommandHandler<CreateMultiChatCommand, Result<ChatCommandDto>> {
    private final ChatDomainService chatDomainService;

    public CreateMultiChatCommandHandler(ChatDomainService chatDomainService) {
        this.chatDomainService = chatDomainService;
    }

    // TODO: 13.05.2023 добавить проверку на то, что приглашённые пользователи не забанили пригласителя
    // INFO: бан запрещает совершать определённые действия с пользователем. Бан нужно проверять во многих сервисах, но
    //  везде он делает одно и то же - запрещает совершать действие, направленное на пользователя. Чтобы писать
    //  один и тот же код для таких проверок, необходимо абстрагировать запреты, накладываемые баном,
    //  от кода конкретных юз кейсов. Есть несколько вариантов, которые сразу приходят на ум:
    //  1. Вынести все действия запрещённые действия в конфиг микросервиса и добавить аннотации над каждой из команд,
    //  которые будут показывать, что за действие выполняет команда. Из самих команд и запросов (query) сделать
    //  пайплайн, в который встроится проверщик этих правил (условно, BanManager).
    //  2. Можно также вынести проверки за репозиторий, чтобы тот получал на вход subject и object и смотрел, забанен ли
    //  subject у object'а (не оч способ).
    /**
     * @param command CQS command for appropriate handler
     * @return {@code Result<ChatCommandDto>} describing whether the result of the function execution was successful.
     * @implNote This method can return {@code Result} with either:<br>
     * {@code ChatNotFoundException},<br>
     * {@code BusinessRuleViolationException}
     */
    @Override
    public Result<ChatCommandDto> handle(@NonNull CreateMultiChatCommand command) {
        var chatName = ChatName.create(command.getChatName());

        if (chatName.isFailure()) {
            return Result.failure(chatName.exceptionOrNull());
        }

        var chat = chatDomainService.createMultiChat(
                chatName.getOrNull(),
                command.getAvatarId(),
                command.getOwnerId(),
                command.getUsersList()
        );

        return Result.success(new ChatCommandDto(chat.getId()));
    }
}
