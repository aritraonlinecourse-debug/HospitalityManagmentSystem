package com.database;

import org.bson.Document;

public class Room {
    private int roomId;
    private int hotelId;
    private String roomNumber;
    private String type;
    private double price;
    private String status;

    public Room() {
    }

    public Room(int roomId, int hotelId, String roomNumber, String type, double price, String status) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Document toDocument() {
        return new Document("_id", roomId)
                .append("hotelId", hotelId)
                .append("roomNumber", roomNumber)
                .append("type", type)
                .append("price", price)
                .append("status", status);
    }

    public static Room fromDocument(Document doc) {
        Room r = new Room();
        r.setRoomId(doc.getInteger("_id"));
        r.setHotelId(doc.getInteger("hotelId"));
        r.setRoomNumber(doc.getString("roomNumber"));
        r.setType(doc.getString("type"));
        r.setPrice(doc.getDouble("price"));
        r.setStatus(doc.getString("status"));
        return r;
    }

    @Override
    public String toString() {
        return "Room [roomId=" + roomId + ", hotelId=" + hotelId + ", roomNumber=" + roomNumber + ", type=" + type
                + ", price=" + price + ", status=" + status + "]";
    }
}