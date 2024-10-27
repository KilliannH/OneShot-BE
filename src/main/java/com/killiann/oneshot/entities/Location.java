package com.killiann.oneshot.entities;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "locations")
public class Location {

    @Id
    private String id;

    @NotBlank
    private String userId;

    // Combine latitude and longitude into a single GeoJSON Point
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    // Default constructor
    public Location() {}

    // Updated constructor to use Point
    public Location(String userId, double lng, double lat) {
        this.userId = userId;
        this.location = new GeoJsonPoint(lng, lat);
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

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(double lng, double lat) {
        this.location = new GeoJsonPoint(lng, lat);
    }
}