package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.application.profile.model.parameter.UserFilterParams;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import com.messenger.authandprofile.domain.exception.user.UserNotFoundException;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.repository.UserRepository;
import com.messenger.authandprofile.infra.domain.persistence.mapper.UserEntityMapper;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa userRepositoryJpa;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(
            UserRepositoryJpa userRepositoryJpa, UserEntityMapper userEntityMapper, PasswordEncoder passwordEncoder
    ) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
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
        var pageRequest = PageRequest.of(pageNumber, pageSize, getUserFilterParamsSorting(userFilterParams));

        var pageUsers = userRepositoryJpa.findAll(UserSpecifications.filterUsers(userFilterParams), pageRequest);

        return pageUsers.map(userEntityMapper::mapToDomainModel).getContent();
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return userRepositoryJpa.existsByEmail(email);
    }

    @Override
    public boolean isExistsByLogin(String login) {
        return userRepositoryJpa.existsByLogin(login);
    }

    @Override
    public void addUser(User user) {
        var userEntity = userEntityMapper.mapToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepositoryJpa.save(userEntity);
        user.clearEvents();
    }

    @Override
    public void updateUser(UUID id, User updatedUser) {
        var optionalUserEntity = userRepositoryJpa.findById(id);
        optionalUserEntity.ifPresentOrElse(userEntity -> {
            userEntityMapper.mapDomainToUserEntity(updatedUser, userEntity);
            userRepositoryJpa.save(userEntity);
            updatedUser.clearEvents();
        }, () -> {
            throw UserNotFoundException.byId(id);
        });
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepositoryJpa.deleteById(id);
    }

    private @NonNull Sort getUserFilterParamsSorting(@NonNull UserFilterParams userFilterParams) {
        return getSortParam("login", userFilterParams.getLogin().getSortingOrder())
                .and(getSortParam("fullName", userFilterParams.getFullName().getSortingOrder()))
                .and(getSortParam("phoneNumber", userFilterParams.getPhoneNumber().getSortingOrder()))
                .and(getSortParam("registrationDate", userFilterParams.getRegistrationDate().getSortingOrder()))
                .and(getSortParam("birthDate", userFilterParams.getBirthDate().getSortingOrder()))
                .and(getSortParam("city", userFilterParams.getCity().getSortingOrder()));
    }

    private @NonNull Sort getSortParam(String propertyName, @NonNull SortingOrder sortingOrder) {
        switch (sortingOrder) {
            case ASC:
                return Sort.by(Sort.Direction.ASC, propertyName);
            case DESC:
                return Sort.by(Sort.Direction.DESC, propertyName);
            default:
                throw new AssertionError(sortingOrder);
        }
    }
}
