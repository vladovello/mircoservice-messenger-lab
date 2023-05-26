package com.messenger.chat.infra.spring.config;

import com.messenger.chat.domain.chat.repository.ChatRepository;
import com.messenger.chat.domain.chat.service.ChatDomainService;
import com.messenger.chat.domain.chat.service.impl.ChatDomainServiceImpl;
import com.messenger.chat.domain.chatparticipant.repository.ChatParticipantRepository;
import com.messenger.chat.domain.message.repository.FileStorageRepository;
import com.messenger.chat.domain.message.repository.MessageRepository;
import com.messenger.chat.domain.message.service.AttachmentDomainService;
import com.messenger.chat.domain.message.service.MessageDomainService;
import com.messenger.chat.domain.message.service.impl.AttachmentDomainServiceImpl;
import com.messenger.chat.domain.message.service.impl.MessageDomainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public ChatDomainService chatDomainService(
            @Autowired ChatParticipantRepository chatParticipantRepository,
            @Autowired ChatRepository chatRepository
    ) {
        return new ChatDomainServiceImpl(chatParticipantRepository, chatRepository);
    }

    @Bean
    public MessageDomainService messageDomainService(
            @Autowired MessageRepository messageRepository,
            @Autowired ChatParticipantRepository chatParticipantRepository
    ) {
        return new MessageDomainServiceImpl(messageRepository, chatParticipantRepository);
    }

    @Bean
    public AttachmentDomainService attachmentDomainService(@Autowired FileStorageRepository fileStorageRepository) {
        return new AttachmentDomainServiceImpl(fileStorageRepository);
    }
}
