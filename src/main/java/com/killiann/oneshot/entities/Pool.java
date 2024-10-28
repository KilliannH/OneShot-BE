package com.killiann.oneshot.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "pools")
public class Pool {
    @Id
    private String id;
    private String userId;
    private Set<String> liked;
    private Set<String> matches;

    public Pool() {}

    public Pool(String userId, Set<String> liked, Set<String> matches) {
        this.userId = userId;
        this.liked = liked;
        this.matches = matches;
    }

    public Pool(String userId) {
        this.userId = userId;
        this.liked = new HashSet<>();
        this.matches = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getLiked() {
        return liked;
    }

    public void setLiked(Set<String> liked) {
        this.liked = liked;
    }

    public Set<String> getMatches() {
        return matches;
    }

    public void setMatches(Set<String> matches) {
        this.matches = matches;
    }
}
