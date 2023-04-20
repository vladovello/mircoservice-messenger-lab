package com.messenger.friendsapp.domain.repository;

import com.messenger.friendsapp.domain.aggregate.Friendship;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Запрос на получение друзей для userId происходит следующим образом:
 *  1. Сначала ищутся все не BLOCKED пользователи в списке Friendship, где requesterId = userId
 *  2. Потом ищутся все пользователи, у которых не стоит BLOCKED и где addresseeId = userId
 */
public interface FriendshipRepository {
    boolean isAddedToFriendList(UUID requesterId, UUID addresseeId);
    Optional<Friendship> findFriend(UUID requesterId, UUID addresseeId);
    FriendshipStatus getFriendshipStatus(UUID requesterId, UUID addresseeId);
    List<Friendship> getAllFriends(UUID userId, boolean withNotMutual);
    void save(Friendship friendship);
    void delete(UUID friendshipId);
}
