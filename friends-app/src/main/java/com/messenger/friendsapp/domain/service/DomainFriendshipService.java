package com.messenger.friendsapp.domain.service;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;
import com.messenger.friendsapp.shared.Unit;
import io.vavr.control.Either;

import java.util.UUID;

public interface DomainFriendshipService {
    Either<UserIsBlockedException, Unit> addToFriendshipList(UUID requesterId, Addressee addressee);
    void removeFromFriendshipList(UUID requesterId, UUID addresseeId);
}
