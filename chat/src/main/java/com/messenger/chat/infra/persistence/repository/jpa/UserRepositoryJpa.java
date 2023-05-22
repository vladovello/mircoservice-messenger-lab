package com.messenger.chat.infra.persistence.repository.jpa;

import com.messenger.chat.domain.user.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<ChatUser, UUID> {
}
