package com.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    private MongoCollection<Document> getCollection() {
        return MongoConnectionManager.getDatabase().getCollection("guests");
    }

    public boolean addGuest(Guest guest) {
        try {
            int newId = Counters.getNextSequence("guestId");
            guest.setGuestId(newId);
            getCollection().insertOne(guest.toDocument());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Guest getGuestById(int id) {
        Document doc = getCollection().find(Filters.eq("_id", id)).first();
        return doc != null ? Guest.fromDocument(doc) : null;
    }

    public boolean updateGuest(Guest guest) {
        try {
            var result = getCollection().updateOne(
                    Filters.eq("_id", guest.getGuestId()),
                    Updates.combine(
                            Updates.set("name", guest.getName()),
                            Updates.set("email", guest.getEmail()),
                            Updates.set("phone", guest.getPhone())
                    )
            );
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGuest(int id) {
        try {
            var result = getCollection().deleteOne(Filters.eq("_id", id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Guest> getAllGuests() {
        List<Guest> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(Guest.fromDocument(doc));
        }
        return list;
    }
}