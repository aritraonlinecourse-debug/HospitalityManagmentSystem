package com.database;

import org.bson.Document;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Reservation {
    private int reservationId;
    private int guestId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalCost;

    public Reservation() {
    }

    public Reservation(int reservationId, int guestId, int roomId, LocalDate checkIn, LocalDate checkOut, double totalCost) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
    }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public Document toDocument() {
        return new Document("_id", reservationId)
                .append("guestId", guestId)
                .append("roomId", roomId)
                .append("checkIn", Date.from(checkIn.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("checkOut", Date.from(checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("totalCost", totalCost);
    }

    public static Reservation fromDocument(Document doc) {
        Reservation r = new Reservation();
        r.setReservationId(doc.getInteger("_id"));
        r.setGuestId(doc.getInteger("guestId"));
        r.setRoomId(doc.getInteger("roomId"));
        r.setCheckIn(((Date) doc.get("checkIn")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        r.setCheckOut(((Date) doc.get("checkOut")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        r.setTotalCost(doc.getDouble("totalCost"));
        return r;
    }

    @Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId +
                ", guestId=" + guestId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", totalCost=" + totalCost + "]";
    }
}