package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.repository.UserRepository;
import com.messenger.authandprofile.infra.domain.persistence.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa userRepositoryJpa;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryImpl(UserRepositoryJpa userRepositoryJpa, UserEntityMapper userEntityMapper) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        var optionalUserEntity = userRepositoryJpa.findById(id);
        return optionalUserEntity.map(userEntityMapper::mapToDomainModel);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        var optionalUserEntity = userRepositoryJpa.findByEmail(email);
        return optionalUserEntity.map(userEntityMapper::mapToDomainModel);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        var optionalUserEntity = userRepositoryJpa.findByLogin(login);
        return optionalUserEntity.map(userEntityMapper::mapToDomainModel);
    }

    @Override
    public List<User> findAllUsersPaginatedWithParams(int pageNumber, int pageSize, UserFilterParams userFilterParams) {

        return null;
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return false;
    }

    @Override
    public boolean isExistsByLogin(String login) {
        return false;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(UUID id, User updatedUser) {

    }

    @Override
    public void deleteUserById(UUID id) {

    }
}
