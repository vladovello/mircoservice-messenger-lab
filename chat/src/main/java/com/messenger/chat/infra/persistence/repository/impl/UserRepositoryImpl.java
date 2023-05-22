package com.messenger.chat.infra.persistence.repository.impl;

import com.messenger.chat.domain.user.ChatUser;
import com.messenger.chat.domain.user.repository.UserRepository;
import com.messenger.chat.infra.persistence.repository.jpa.UserRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa userRepositoryJpa;

    public UserRepositoryImpl(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<ChatUser> getUser(UUID userId) {
        return userRepositoryJpa.findById(userId);
    }

    @Override
    public void save(ChatUser user) {
        userRepositoryJpa.save(user);
    }
}
