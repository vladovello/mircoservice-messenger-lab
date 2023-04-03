package com.messenger.authandprofile.domain.exception;

public class InvalidEmail extends Exception {
    public InvalidEmail(String emailAddress) {
        super(String.format("Address '%s' is not legal for email", emailAddress));
    }
}
