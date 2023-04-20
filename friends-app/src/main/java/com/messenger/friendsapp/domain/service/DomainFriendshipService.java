package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Friendship;
import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;

import java.util.List;
import java.util.UUID;

public interface DomainFriendshipService {
    List<Friendship> getAllMutualFriends(UUID userId);
    List<Friendship> getAllFriends(UUID userId);
    List<Friendship> findAllFriendsPaginatedByFullName(int pageNumber, int pageSize, UUID userId, String fullName);
    void addToFriendshipList(UUID requesterId, Addressee addressee) throws UserIsBlockedException;
    void removeFromFriendshipList(UUID requesterId, UUID addresseeId);
}
