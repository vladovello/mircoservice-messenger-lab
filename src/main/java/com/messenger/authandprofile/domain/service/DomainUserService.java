package com.messenger.authandprofile.domain.service;

import com.messenger.authandprofile.domain.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface DomainUserService {
    Optional<User> getOtherUserProfile(UUID selfUserId, UUID otherUserId);
}
