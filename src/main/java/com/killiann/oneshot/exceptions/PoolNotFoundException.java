package com.killiann.oneshot.exceptions;

public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException(String id) {
        super("Could not find pool with id or userId: " + id);
    }
}