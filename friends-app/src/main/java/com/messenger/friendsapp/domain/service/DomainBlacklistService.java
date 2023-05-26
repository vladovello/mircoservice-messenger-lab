package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Addressee;

import java.util.UUID;

public interface DomainBlacklistService {
    /**
     * Adds user to requester's blacklist
     * @param requesterId the one who is adding to his blacklist
     * @param addressee the one who is being added in blacklist
     */
    void addToBlacklist(UUID requesterId, Addressee addressee);

    /**
     * Removes user from requester's friend list
     * @param requesterId the one who is removing to his blacklist
     * @param addresseeId the one who is removing in blacklist
     */
    void removeFromBlacklist(UUID requesterId, UUID addresseeId);
}
