package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;

import java.util.UUID;

public interface DomainFriendshipService {
    void addToFriendshipList(UUID requesterId, Addressee addressee) throws UserIsBlockedException;
    void removeFromFriendshipList(UUID requesterId, UUID addresseeId);
}
