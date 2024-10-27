package com.killiann.oneshot.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pools")
public class Pool {
    @Id
    private String id;
    private String userId;
    private List<String> liked;
    private List<String> matches;

    Pool(String userId, List<String> liked, List<String> matches) {
        this.userId = userId;
        this.liked = liked;
        this.matches = matches;
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

    public List<String> getLiked() {
        return liked;
    }

    public void setLiked(List<String> liked) {
        this.liked = liked;
    }

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }
}
