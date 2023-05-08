package com.messenger.chat.domain.message.service;

import com.messenger.chat.domain.message.Message;

public interface MessageDomainService {
    boolean createMessage(Message message);
}
