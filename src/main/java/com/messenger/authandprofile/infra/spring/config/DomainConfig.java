package com.messenger.authandprofile.infra.spring.config;

import com.messenger.authandprofile.domain.repository.FriendRepository;
import com.messenger.authandprofile.domain.repository.UserRepository;
import com.messenger.authandprofile.domain.service.DomainUserService;
import com.messenger.authandprofile.domain.service.impl.DomainUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public DomainUserService domainUserService(@Autowired UserRepository userRepository,
            @Autowired FriendRepository friendRepository) {
        return new DomainUserServiceImpl(userRepository, friendRepository);
    }
}
