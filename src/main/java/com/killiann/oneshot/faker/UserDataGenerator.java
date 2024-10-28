package com.killiann.oneshot.faker;

import com.github.javafaker.Faker;
import com.killiann.oneshot.configs.SecurityConfig;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDataGenerator {

    private final MongoCollection<Document> usersCollection;
    private final Faker faker = new Faker();
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(MongoCollection<Document> usersCollection) {
        this.usersCollection = usersCollection;
        this.passwordEncoder = SecurityConfig.passwordEncoder();
    }

    public List<String> generateUsers(int count) {
        List<String> userIds = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String rawPassword = "salut";
            String encryptedPassword = passwordEncoder.encode(rawPassword);

            Document user = new Document()
                    .append("username", faker.name().username())
                    .append("email", faker.internet().emailAddress())
                    .append("password", encryptedPassword)
                    .append("roles", Arrays.asList(new Document("_id", "1").append("name", "ROLE_USER")));

            usersCollection.insertOne(user);
            userIds.add(user.getObjectId("_id").toString()); // Capture the generated ObjectId

            System.out.println("Generated user with username: " + user.getString("username") + ", Password: " + rawPassword);
        }

        return userIds; // Return list of user IDs
    }
}