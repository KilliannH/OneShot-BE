package com.killiann.oneshot.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(value = "profiles")
public class Profile {
    @Id
    private String id;
    @NotBlank
    private String userId;
    private String bio;
    private String job;
    @NotBlank
    private Long birthday;

    @NotBlank
    private String displayName;
    @NotBlank
    private String gender;

    private String interestedIn;

    private String imageUrl;

    public Profile() {}

    public Profile(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public Profile(String userId, String bio, String job, Long birthday, String displayName, String gender, String interestedIn, String imageUrl) {
        this.userId = userId;
        this.bio = bio;
        this.job = job;
        this.birthday = birthday;
        this.displayName = displayName;
        this.gender = gender;
        this.interestedIn = interestedIn;
        this.imageUrl = imageUrl;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
