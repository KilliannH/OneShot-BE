package com.killiann.oneshot.faker;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LocationDataGenerator {

    private final MongoCollection<Document> locationsCollection;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public LocationDataGenerator(MongoCollection<Document> locationsCollection) {
        this.locationsCollection = locationsCollection;
    }

    public void generateLocations(List<String> userIds) {
        List<Double> location1 = Arrays.asList(-122.084, 37.4219983);
        List<Double> location2 = Arrays.asList(-102.52554, 26.85215);

        for (String userId : userIds) {
            // Randomly choose between location1 and location2
            List<Double> chosenLocation = random.nextBoolean() ? location1 : location2;

            Document location = new Document()
                    .append("userId", userId)
                    .append("location", new Document("type", "Point")
                            .append("coordinates", chosenLocation));

            locationsCollection.insertOne(location);
        }
    }
}