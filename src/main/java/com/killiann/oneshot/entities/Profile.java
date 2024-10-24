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
    private Character gender;

    private List<String> imageUrls = new ArrayList<>();

    public Profile() {}

    public Profile(String userId, String bio, String job, Long birthday, String displayName, Character gender, List<String> imageUrls) {
        this.userId = userId;
        this.bio = bio;
        this.job = job;
        this.birthday = birthday;
        this.displayName = displayName;
        this.gender = gender;
        this.imageUrls = imageUrls;
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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
