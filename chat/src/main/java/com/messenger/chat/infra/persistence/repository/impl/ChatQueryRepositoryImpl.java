package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.application.command.exception.UnexpectedException;
import com.messenger.chat.application.query.dto.ChatMessagePreview;
import com.messenger.chat.application.query.dto.PreviewChatInfo;
import com.messenger.chat.application.query.repository.ChatQueryRepository;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.infra.persistence.repository.jpa.ChatParticipantRepositoryJpa;
import com.messenger.chat.infra.persistence.repository.jpa.ChatRepositoryJpa;
import com.messenger.chat.infra.persistence.repository.jpa.MessageRepositoryJpa;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ChatQueryRepositoryImpl implements ChatQueryRepository {
    private final ChatRepositoryJpa chatRepositoryJpa;
    private final ChatParticipantRepositoryJpa chatParticipantRepositoryJpa;
    private final MessageRepositoryJpa messageRepositoryJpa;

    public ChatQueryRepositoryImpl(
            ChatRepositoryJpa chatRepositoryJpa,
            ChatParticipantRepositoryJpa chatParticipantRepositoryJpa,
            MessageRepositoryJpa messageRepositoryJpa
    ) {
        this.chatRepositoryJpa = chatRepositoryJpa;
        this.chatParticipantRepositoryJpa = chatParticipantRepositoryJpa;
        this.messageRepositoryJpa = messageRepositoryJpa;
    }

    @Override
    public List<PreviewChatInfo> getUserChatsPaginated(
            UUID requesterId,
            int pageNumber,
            int pageSize,
            String chatName
    ) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);

        // TODO: 17.05.2023 сейчас возвращаются все чаты. Возможно стоит поменять, чтобы возвращались только чаты, в которых состоит пользователь
        var chatPage = chatRepositoryJpa.findAll(hasChatNameLike(chatName), pageRequest);
        var previewChatInfoList = new ArrayList<PreviewChatInfo>();

        chatPage.get().forEach(chat -> {
            var optionalMessage = messageRepositoryJpa.findFirstByChatIdOrderByCreationDateDesc(chat.getId());
            PreviewChatInfo previewChatInfo;

            if (optionalMessage.isEmpty()) {
                previewChatInfo = new PreviewChatInfo(
                        chat.getId(),
                        resolveChatName(chat, requesterId),
                        null
                );
            } else {
                var message = optionalMessage.get();
                previewChatInfo = new PreviewChatInfo(
                        chat.getId(),
                        resolveChatName(chat, requesterId),
                        new ChatMessagePreview(
                                message.getMessageText().getText(),
                                message.getCreationDate(),
                                message.getSenderUserId()
                        )
                );
            }

            previewChatInfoList.add(previewChatInfo);
        });

        return previewChatInfoList;
    }

    @Override
    public List<PreviewChatInfo> getUserMessagesPaginated(
            UUID requesterId,
            int pageNumber,
            int pageSize,
            String messageText
    ) {
        // TODO: 16.05.2023 change hard-coded text to generated entity call
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));

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

    private String resolveChatName(@NonNull Chat chat, UUID requesterId) {
        if (chat.getChatType() != ChatType.DIALOGUE) return chat.getChatName().getName();

        var participants = chatParticipantRepositoryJpa.findAllByChatId(chat.getId());
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

    private static @NonNull Specification<Chat> hasChatNameLike(String chatName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get("chatName"),
                "%" + chatName + "%"
        );
    }

    private static @NonNull Specification<Message> hasMessageTextLike(String messageText) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get("messageText"),
                "%" + messageText + "%"
        );
    }
}

/*
 * select chatId, chatName, messageText, sendingDate, senderId, hasAttachment, attachmentName
 * from chat
 * join message on message.creationDate = max(message.creationDate)
 */