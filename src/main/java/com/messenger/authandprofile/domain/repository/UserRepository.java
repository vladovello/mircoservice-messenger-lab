package com.messenger.authandprofile.domain.repository;

import com.messenger.authandprofile.domain.model.entity.User;

import java.util.UUID;

public interface UserRepository {
    User findUserById(UUID id);
    User findUserByEmail(String email);
    User findUserByLogin(String login);
    boolean isExistsByEmail(String email);
    boolean isExistsByLogin(String login);
    void saveUser(User user);
    void updateUser(UUID id, User user);
    void deleteUserById(UUID id);
}