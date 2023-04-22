package com.messenger.friendsapp.infra.spring.config;

import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.service.DomainBlacklistService;
import com.messenger.friendsapp.domain.service.DomainFriendshipService;
import com.messenger.friendsapp.domain.service.impl.DomainBlacklistServiceImpl;
import com.messenger.friendsapp.domain.service.impl.DomainFriendshipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public DomainFriendshipService domainFriendshipService(@Autowired FriendshipRepository friendshipRepository,
            @Autowired BlacklistRepository blacklistRepository) {
        return new DomainFriendshipServiceImpl(friendshipRepository, blacklistRepository);
    }

    @Bean
    public DomainBlacklistService domainBlacklistService(@Autowired BlacklistRepository blacklistRepository) {
        return new DomainBlacklistServiceImpl(blacklistRepository);
    }
}
