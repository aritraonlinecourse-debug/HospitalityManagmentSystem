package com.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RoomsDAO {

    private MongoCollection<Document> getCollection() {
        return MongoConnectionManager.getDatabase().getCollection("rooms");
    }

    public boolean addRoom(Room room) {
        try {
            int newId = Counters.getNextSequence("roomId");
            room.setRoomId(newId);
            getCollection().insertOne(room.toDocument());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Room getRoomById(int id) {
        Document doc = getCollection().find(Filters.eq("_id", id)).first();
        return doc != null ? Room.fromDocument(doc) : null;
    }

    public boolean updateRoom(Room room) {
        try {
            var result = getCollection().updateOne(
                    Filters.eq("_id", room.getRoomId()),
                    Updates.combine(
                            Updates.set("hotelId", room.getHotelId()),
                            Updates.set("roomNumber", room.getRoomNumber()),
                            Updates.set("type", room.getType()),
                            Updates.set("price", room.getPrice()),
                            Updates.set("status", room.getStatus())
                    )
            );
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRoom(int id) {
        try {
            var result = getCollection().deleteOne(Filters.eq("_id", id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(Room.fromDocument(doc));
        }
        return list;
    }
}