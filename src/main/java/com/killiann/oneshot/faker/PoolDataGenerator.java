package com.killiann.oneshot.faker;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoolDataGenerator {

    private final MongoCollection<Document> poolsCollection;
    private final Faker faker = new Faker();

    public PoolDataGenerator(MongoCollection<Document> poolsCollection) {
        this.poolsCollection = poolsCollection;
    }

    public void generatePools(List<String> userIds) {
        for (String userId : userIds) {
            Document pool = new Document()
                    .append("userId", userId)
                    .append("liked", new ArrayList<>())
                    .append("matches", new ArrayList<>());
            poolsCollection.insertOne(pool);
        }
    }
}