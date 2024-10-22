package com.killiann.oneshot.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String id) {
        super("Could not find profile with id or userId: " + id);
    }
}