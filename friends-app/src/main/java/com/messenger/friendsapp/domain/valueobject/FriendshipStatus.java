package com.messenger.friendsapp.domain.valueobject;

public enum FriendshipStatus {
    // Addressee not responded to the Requester. Requester state.
    PENDING,
    // Addressee responded to the Requester. Both Requester and Addressee state.
    MUTUAL
}
