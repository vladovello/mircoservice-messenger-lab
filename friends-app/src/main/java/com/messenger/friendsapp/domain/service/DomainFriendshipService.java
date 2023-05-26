package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;

import java.util.UUID;

public interface DomainFriendshipService {
    /**
     * Adds user to requester's friend list
     * @param requesterId the one who is adding to his friend list
     * @param addressee the one who is being added in friend list
     * @throws UserIsBlockedException thrown if requester is added to blacklist by addressee
     */
    void addToFriendshipList(UUID requesterId, Addressee addressee) throws UserIsBlockedException;
    /**
     * Removes user from requester's friend list
     * @param requesterId the one who is removing to his friend list
     * @param addresseeId the one who is removed in friend list
     */
    void removeFromFriendshipList(UUID requesterId, UUID addresseeId);
}
