package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity>,
        PagingAndSortingRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
