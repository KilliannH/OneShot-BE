package com.killiann.oneshot.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String id) {
        super("Could not find location with id or userId: " + id);
    }
}