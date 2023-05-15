package com.messenger.chat.infra.persistence.repository;

import com.messenger.chat.application.command.exception.UnexpectedException;
import com.messenger.chat.application.query.dto.ChatMessagePreview;
import com.messenger.chat.application.query.dto.PreviewChatInfo;
import com.messenger.chat.application.query.repository.MessageQueryRepository;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.message.Message;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageQueryRepositoryImpl implements MessageQueryRepository {
    private final ChatRepositoryJpa chatRepositoryJpa;
    private final ChatParticipantRepositoryJpa chatParticipantRepositoryJpa;
    private final MessageRepositoryJpa messageRepositoryJpa;

    public MessageQueryRepositoryImpl(
            ChatRepositoryJpa chatRepositoryJpa,
            ChatParticipantRepositoryJpa chatParticipantRepositoryJpa,
            MessageRepositoryJpa messageRepositoryJpa
    ) {
        this.chatRepositoryJpa = chatRepositoryJpa;
        this.chatParticipantRepositoryJpa = chatParticipantRepositoryJpa;
        this.messageRepositoryJpa = messageRepositoryJpa;
    }


    @Override
    public List<PreviewChatInfo> getMessagesPaginated(
            UUID requesterId,
            int pageNumber,
            int pageSize,
            String messageText
    ) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);

        var messagePage = messageRepositoryJpa.findAll(hasMessageTextLike(messageText), pageRequest);
        var previewChatInfoList = new ArrayList<PreviewChatInfo>();

        messagePage.get().forEach(message -> {
            var optionalChat = chatRepositoryJpa.findById(message.getChatId());

            if (optionalChat.isEmpty()) {
                throw new UnexpectedException(String.format("Chat with id '%s' does not exists", message.getChatId()));
            }

            var chat = optionalChat.get();

            previewChatInfoList.add(new PreviewChatInfo(
                    chat.getId(),
                    resolveChatName(chat, requesterId),
                    new ChatMessagePreview(
                            message.getMessageText().getText(),
                            message.getCreationDate(),
                            message.getSenderUserId()
                    )
            ));
        });

        return previewChatInfoList;
    }

    // TODO: 16.05.2023 refactor this shit
    private String resolveChatName(@NonNull Chat chat, UUID requesterId) {
        if (chat.getChatType() != ChatType.DIALOGUE) return chat.getChatName().getName();

        var participants = chatParticipantRepositoryJpa.getAllByChatId(chat.getId());
        var optionalSecondDialogueUser = participants
                .stream()
                .filter(chatParticipant -> chatParticipant.getUserId() != requesterId)
                .findFirst();

        if (optionalSecondDialogueUser.isEmpty()) {
            throw new UnexpectedException(String.format("Dialogue chat with id '%s' has no second user", chat.getId()));
        }

        var secondDialogueUser = optionalSecondDialogueUser.get();

        return secondDialogueUser.getUser().getFullName().getValue();
    }

    private static @NonNull Specification<Message> hasMessageTextLike(String messageText) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get("messageText"),
                "%" + messageText + "%"
        );
    }
}
