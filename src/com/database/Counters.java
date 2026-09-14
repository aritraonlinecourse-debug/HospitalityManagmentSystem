package com.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class Counters {

    public static int getNextSequence(String counterName) {

        MongoCollection<Document> countersCollection =
                MongoConnectionManager.getDatabase().getCollection("counters");

        Document result = countersCollection.findOneAndUpdate(
                new Document("_id", counterName),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions()
                        .returnDocument(ReturnDocument.AFTER)
                        .upsert(true)
        );

        return result.getInteger("seq");
    }
}