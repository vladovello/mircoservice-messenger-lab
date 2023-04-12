package com.messenger.authandprofile.domain.repository;

import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.domain.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User findUserById(UUID id);

    User findUserByEmail(String email);

    User findUserByLogin(String login);

    List<User> findAllUsersPaginatedWithParams(int pageNumber, int pageSize, UserFilterParams userFilterParams);

    boolean isExistsByEmail(String email);

    boolean isExistsByLogin(String login);

    void addUser(User user);

    void updateUser(UUID id, User updatedUser);

    void deleteUserById(UUID id);
}