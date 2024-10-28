package com.killiann.oneshot.faker;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class LocationDataGenerator {

    private final MongoCollection<Document> locationsCollection;
    private final Faker faker = new Faker();

    public LocationDataGenerator(MongoCollection<Document> locationsCollection) {
        this.locationsCollection = locationsCollection;
    }

    public void generateLocations(List<String> userIds) {
        for (String userId : userIds) {
            double latitude = Double.parseDouble(faker.address().latitude());
            double longitude = Double.parseDouble(faker.address().longitude());

            Document location = new Document()
                    .append("userId", userId)
                    .append("location", new Document("type", "Point")
                            .append("coordinates", Arrays.asList(longitude, latitude)));
            locationsCollection.insertOne(location);
        }
    }
}