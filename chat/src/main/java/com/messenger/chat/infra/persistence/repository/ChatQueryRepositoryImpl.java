package com.messenger.chat.infra.persistence.repository;

import com.messenger.chat.application.command.exception.UnexpectedException;
import com.messenger.chat.application.query.dto.ChatMessagePreview;
import com.messenger.chat.application.query.dto.PreviewChatInfo;
import com.messenger.chat.application.query.repository.ChatQueryRepository;
import com.messenger.chat.domain.chat.Chat;
import com.messenger.chat.domain.chat.valueobject.ChatType;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    // TODO: 16.05.2023 возможно можно сделать через JOIN и спецификации
    @Override
    public List<PreviewChatInfo> getChatsPaginated(UUID requesterId, int pageNumber, int pageSize, String chatName) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);

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

    private static @NonNull Specification<Chat> hasChatNameLike(String chatName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get("chatName"),
                "%" + chatName + "%"
        );
    }
}

/*
 * select chatId, chatName, messageText, sendingDate, senderId, hasAttachment, attachmentName
 * from chat
 * join message on message.creationDate = max(message.creationDate)
 */