package com.killiann.oneshot.controllers.dto;

public class LocationDto {
    private String userId;
    private String lng;
    private String lat;
    private Double radius;

    public LocationDto() {}

    public LocationDto(String userId, String lng, String lat, Double radius) {
        this.userId = userId;
        this.lng = lng;
        this.lat = lat;
        this.radius = radius;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
