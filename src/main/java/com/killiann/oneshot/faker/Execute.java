package com.killiann.oneshot.faker;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.List;

public class Execute {

    public static void main(String[] args) {
        try (MongoClient client = MongoDBConnection.connect()) {
            MongoDatabase database = MongoDBConnection.getDatabase(client);

            UserDataGenerator userDataGenerator = new UserDataGenerator(database.getCollection("users"));
            List<String> userIds = userDataGenerator.generateUsers(15);

            ProfileDataGenerator profileDataGenerator = new ProfileDataGenerator(database.getCollection("profiles"));
            profileDataGenerator.generateProfiles(userIds);

            LocationDataGenerator locationDataGenerator = new LocationDataGenerator(database.getCollection("locations"));
            locationDataGenerator.generateLocations(userIds);

            PoolDataGenerator poolDataGenerator = new PoolDataGenerator(database.getCollection("pools"));
            poolDataGenerator.generatePools(userIds);
        }
    }
}