package com.killiann.oneshot.faker;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class ProfileDataGenerator {

    private final MongoCollection<Document> profilesCollection;
    private final Faker faker = new Faker();

    public ProfileDataGenerator(MongoCollection<Document> profilesCollection) {
        this.profilesCollection = profilesCollection;
    }

    public void generateProfiles(List<String> userIds) {
        String[] genders = {"male", "female"};
        String[] interestedInOptions = {"male", "female"};

        for (String userId : userIds) { // Use real userId from Users collection
            Document profile = new Document()
                    .append("userId", userId)
                    .append("bio", faker.lorem().sentence())
                    .append("job", faker.job().title())
                    .append("birthday", faker.date().birthday().getTime())
                    .append("displayName", faker.name().fullName())
                    .append("gender", genders[faker.random().nextInt(genders.length)])
                    .append("interestedIn", interestedInOptions[faker.random().nextInt(interestedInOptions.length)])
                    .append("imageUrls", Arrays.asList(faker.internet().avatar()));

            profilesCollection.insertOne(profile);
        }
    }
}