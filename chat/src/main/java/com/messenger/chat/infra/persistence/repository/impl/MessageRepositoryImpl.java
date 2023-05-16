package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.repository.MessageRepository;
import com.messenger.chat.infra.persistence.repository.OffsetPageRequest;
import com.messenger.chat.infra.persistence.repository.jpa.MessageRepositoryJpa;
import lombok.NonNull;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageRepositoryImpl implements MessageRepository {
    private final MessageRepositoryJpa messageRepositoryJpa;

    public MessageRepositoryImpl(MessageRepositoryJpa messageRepositoryJpa) {
        this.messageRepositoryJpa = messageRepositoryJpa;
    }

    @Override
    public Optional<Message> getById(UUID id) {
        return messageRepositoryJpa.findById(id);
    }

    @Override
    public List<Message> getMessagesByDate(UUID chatId, @NonNull LocalDate date) {
        return messageRepositoryJpa.findAllByChatIdAndCreationDateBetween(
                chatId,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public List<Message> getMessagesPaginated(UUID chatId, int offset) {
        var pageRequest = new OffsetPageRequest(offset, 50, Sort.by(Sort.Direction.DESC, "creationDate"));
        var messagePage = messageRepositoryJpa.findAllByChatId(pageRequest, chatId);

        return messagePage.get().collect(Collectors.toList());
    }

    @Override
    public void save(Message message) {
        messageRepositoryJpa.save(message);
    }

    @Override
    public void delete(@NonNull Message message) {
        messageRepositoryJpa.deleteById(message.getId());
    }
}
