package com.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private MongoCollection<Document> getCollection() {
        return MongoConnectionManager.getDatabase().getCollection("reservations");
    }

    public boolean addReservation(Reservation r) {
        try {
            int newId = Counters.getNextSequence("reservationId");
            r.setReservationId(newId);
            getCollection().insertOne(r.toDocument());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reservation getReservationById(int id) {
        Document doc = getCollection().find(Filters.eq("_id", id)).first();
        return doc != null ? Reservation.fromDocument(doc) : null;
    }

    public boolean updateReservation(Reservation r) {
        try {
            var result = getCollection().updateOne(
                    Filters.eq("_id", r.getReservationId()),
                    Updates.combine(
                            Updates.set("guestId", r.getGuestId()),
                            Updates.set("roomId", r.getRoomId()),
                            Updates.set("checkIn", r.toDocument().get("checkIn")),
                            Updates.set("checkOut", r.toDocument().get("checkOut")),
                            Updates.set("totalCost", r.getTotalCost())
                    )
            );
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReservation(int id) {
        try {
            var result = getCollection().deleteOne(Filters.eq("_id", id));
            return result.getDeletedCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(Reservation.fromDocument(doc));
        }
        return list;
    }
}