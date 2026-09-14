package com.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionManager {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    private static final String DATABASE_NAME = "hospitality_management_system";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private MongoConnectionManager() {
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}