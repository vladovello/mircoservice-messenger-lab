package com.messenger.friendsapp.domain.exception;

public class UserIsBlockedException extends Exception {
    public UserIsBlockedException() {
    }

    public UserIsBlockedException(String message) {
        super(message);
    }
}
