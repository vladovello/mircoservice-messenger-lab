package com.messenger.authandprofile.domain.repository;

import java.util.UUID;

public interface FriendRepository {
    /**
     * @param userId  id of the user being checked for being blacklisted
     * @param otherId user whose blacklist is being checked
     * @return `true` if user is in other's blacklist and `false` otherwise
     * @throws com.messenger.authandprofile.domain.exception.user.UserNotFoundException thrown when any of users was not found
     */
    boolean isUserInOthersBlacklist(UUID userId, UUID otherId);
}
