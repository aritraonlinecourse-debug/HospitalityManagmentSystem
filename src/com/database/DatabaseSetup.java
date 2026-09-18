package com.database;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

public class DatabaseSetup {

    public static void main(String[] args) {
        MongoDatabase db = MongoConnectionManager.getDatabase();

        createHotelsCollection(db);
        createRoomsCollection(db);
        createGuestsCollection(db);
        createReservationsCollection(db);
        seedCounters(db);

        System.out.println("Database setup complete.");
        MongoConnectionManager.close();
    }

    private static void createHotelsCollection(MongoDatabase db) {
        Document schema = new Document("$jsonSchema", new Document()
                .append("bsonType", "object")
                .append("required", java.util.List.of("_id", "name", "location", "singleRoomPrice", "coupleRoomPrice", "familyRoomPrice"))
                .append("properties", new Document()
                        .append("_id", new Document("bsonType", "int"))
                        .append("name", new Document("bsonType", "string"))
                        .append("location", new Document("bsonType", "string"))
                        .append("amenities", new Document("bsonType", "string"))
                        .append("singleRoomPrice", new Document("bsonType", java.util.List.of("double", "int")).append("minimum", 0))
                        .append("coupleRoomPrice", new Document("bsonType", java.util.List.of("double", "int")).append("minimum", 0))
                        .append("familyRoomPrice", new Document("bsonType", java.util.List.of("double", "int")).append("minimum", 0))
                )
        );
        db.createCollection("hotels", new CreateCollectionOptions()
                .validationOptions(new ValidationOptions().validator(schema)));
        System.out.println("Created 'hotels' collection with validation.");
    }

    private static void createRoomsCollection(MongoDatabase db) {
        Document schema = new Document("$jsonSchema", new Document()
                .append("bsonType", "object")
                .append("required", java.util.List.of("_id", "hotelId", "roomNumber", "type", "price", "status"))
                .append("properties", new Document()
                        .append("_id", new Document("bsonType", "int"))
                        .append("hotelId", new Document("bsonType", "int"))
                        .append("roomNumber", new Document("bsonType", "string"))
                        .append("type", new Document("bsonType", "string"))
                        .append("price", new Document("bsonType", java.util.List.of("double", "int"))
                                .append("minimum", 0))
                        .append("status", new Document("bsonType", "string")
                                .append("enum", java.util.List.of("available", "occupied", "maintenance")))
                )
        );
        db.createCollection("rooms", new CreateCollectionOptions()
                .validationOptions(new ValidationOptions().validator(schema)));
        System.out.println("Created 'rooms' collection with validation.");
    }

    private static void createGuestsCollection(MongoDatabase db) {
        Document schema = new Document("$jsonSchema", new Document()
                .append("bsonType", "object")
                .append("required", java.util.List.of("_id", "name", "email"))
                .append("properties", new Document()
                        .append("_id", new Document("bsonType", "int"))
                        .append("name", new Document("bsonType", "string"))
                        .append("email", new Document("bsonType", "string"))
                        .append("phone", new Document("bsonType", "string"))
                )
        );
        db.createCollection("guests", new CreateCollectionOptions()
                .validationOptions(new ValidationOptions().validator(schema)));

        db.getCollection("guests").createIndex(
                Indexes.ascending("email"),
                new IndexOptions().unique(true)
        );
        System.out.println("Created 'guests' collection with validation and unique email index.");
    }

    private static void createReservationsCollection(MongoDatabase db) {
        Document schema = new Document("$jsonSchema", new Document()
                .append("bsonType", "object")
                .append("required", java.util.List.of("_id", "guestId", "roomId", "checkIn", "checkOut", "totalCost"))
                .append("properties", new Document()
                        .append("_id", new Document("bsonType", "int"))
                        .append("guestId", new Document("bsonType", "int"))
                        .append("roomId", new Document("bsonType", "int"))
                        .append("checkIn", new Document("bsonType", "date"))
                        .append("checkOut", new Document("bsonType", "date"))
                        .append("totalCost", new Document("bsonType", java.util.List.of("double", "int"))
                                .append("minimum", 0))
                )
        );
        db.createCollection("reservations", new CreateCollectionOptions()
                .validationOptions(new ValidationOptions().validator(schema)));

        db.getCollection("reservations").createIndex(
                Indexes.ascending("guestId", "roomId")
        );
        System.out.println("Created 'reservations' collection with validation and compound index.");
    }

    private static void seedCounters(MongoDatabase db) {
        var counters = db.getCollection("counters");
        String[] names = {"hotelId", "roomId", "guestId", "reservationId"};
        for (String name : names) {
            counters.updateOne(
                    new Document("_id", name),
                    new Document("$setOnInsert", new Document("seq", 0)),
                    new com.mongodb.client.model.UpdateOptions().upsert(true)
            );
        }
        System.out.println("Seeded counters collection.");
    }
}