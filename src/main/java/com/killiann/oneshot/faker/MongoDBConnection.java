package com.killiann.oneshot.faker;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; // update with your connection string
    private static final String DATABASE_NAME = "oneshot-db"; // update with your database name

    public static MongoClient connect() {
        return MongoClients.create(CONNECTION_STRING);
    }

    public static MongoDatabase getDatabase(MongoClient client) {
        return client.getDatabase(DATABASE_NAME);
    }
}