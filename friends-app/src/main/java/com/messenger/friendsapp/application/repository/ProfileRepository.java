package com.messenger.friendsapp.application.repository;

import com.messenger.friendsapp.application.dto.FullNameDto;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    Optional<FullNameDto> getUsernameById(UUID userId);
}
