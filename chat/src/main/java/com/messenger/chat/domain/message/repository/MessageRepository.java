package com.messenger.chat.domain.message.repository;

import com.messenger.chat.domain.message.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    Optional<Message> getById(UUID id);
    List<Message> getMessagesByDate(UUID chatId, LocalDate date);
    List<Message> getMessagesPaginated(UUID chatId, int offset);
    void save(Message message);
    void delete(Message message);
}
