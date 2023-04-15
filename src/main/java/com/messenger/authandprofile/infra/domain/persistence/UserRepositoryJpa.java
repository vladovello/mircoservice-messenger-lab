package com.messenger.authandprofile.infra.domain.persistence;

import com.messenger.authandprofile.infra.domain.persistence.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity>,
        PagingAndSortingRepository<UserEntity, UUID> {
    @NonNull Page<UserEntity> findAll(@NonNull Pageable pageable, Specification<UserEntity> specification);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
