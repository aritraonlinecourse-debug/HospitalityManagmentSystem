package com.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private MongoCollection<Document> getCollection() {
        return MongoConnectionManager.getDatabase().getCollection("hotels");
    }

    public boolean addHotel(Hotel hotel) {
        try {
            int newId = Counters.getNextSequence("hotelId");
            hotel.setHotelId(newId);
            getCollection().insertOne(hotel.toDocument());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Hotel getHotelById(int id) {
        Document doc = getCollection().find(Filters.eq("_id", id)).first();
        return doc != null ? Hotel.fromDocument(doc) : null;
    }

    public boolean updateHotel(Hotel hotel) {
        try {
            var result = getCollection().updateOne(
                    Filters.eq("_id", hotel.getHotelId()),
                    Updates.combine(
                            Updates.set("name", hotel.getName()),
                            Updates.set("location", hotel.getLocation()),
                            Updates.set("amenities", hotel.getAmenities()),
                            Updates.set("singleRoomPrice", hotel.getSingleRoomPrice()),
                            Updates.set("coupleRoomPrice", hotel.getCoupleRoomPrice()),
                            Updates.set("familyRoomPrice", hotel.getFamilyRoomPrice())
                    )
            );
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHotel(int id) {
        try {
            var result = getCollection().deleteOne(Filters.eq("_id", id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(Hotel.fromDocument(doc));
        }
        return list;
    }
}